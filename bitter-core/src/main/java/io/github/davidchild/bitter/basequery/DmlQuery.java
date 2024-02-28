package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.connection.DataAccess;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.ModelException;
import io.github.davidchild.bitter.parbag.ExecuteParBagInsert;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreUtils;

import java.util.List;

public class DmlQuery extends  BaseQuery {

    /**
     * If it is a self growing entity INSERT submission, - 1l returns a failure and a successful self increasing primary
     * key ID. n If it is a none self increasing entity, a 1l code is returned indicating success, and - 1l indicates
     * failure. n Batch statements, - 1l represents failure, 1 represents success n Batch adding returns 1 for success,
     * - 1 for failure n If the value is UPDATE, DELETE statement, the number of affected rows will be returned.
     * Otherwise, - 1l represents failure, and the number of affected rows will be returned successfully. n Transaction
     * execution: returns 1 if successful, and - 1 if unsuccessful; The exception record is output in the log
     */
    public Long submit() {
        Exception ex = null;
        if((this.getExecuteParBag().getExecuteEnum() == ExecuteEnum.Delete || this.getExecuteParBag().getExecuteEnum() == ExecuteEnum.Update) && this.getExecuteParBag().getData() != null){
            DataValue key = CoreUtils.getTypeKey(this.getExecuteParBag().getModelType(), this.getExecuteParBag().getData());
            if(key == null)
                throw  new ModelException("bitter checked the model entity ( "+ this.getExecuteParBag().getModelType().getName() +" ) have not the tableId annotation. ple set  the \" @TableId \"  annotation for the database model class.");
        }
        try {
            convert();
        } catch (Exception exx) {
            BitterLogUtil.getInstance().error("bitter-executor-convert-error:" + exx.getMessage());
            return -1L;
        }
        long affectedCount = -1L;
        try {
            if (this.getExecuteParBag().getExecuteEnum() == ExecuteEnum.Insert) {
                long aff = DataAccess.executeScalarToWriterOnlyForInsertReturnIdentity(this);
                if (aff == -1L)
                    return aff;
                if (!((ExecuteParBagInsert) this.getExecuteParBag()).isOutIdentity()) {
                    return 1L;
                }
                affectedCount = aff;
            } else if (this.getExecuteParBag().getExecuteEnum() == ExecuteEnum.BachInsert) {
                affectedCount = DataAccess.executeInsertBach(this);
            } else if (this.getExecuteParBag().getExecuteEnum() == ExecuteEnum.Scope) {
                if (!this.isScopeSuccess())
                    return -1L;
                affectedCount = DataAccess.executeScope(this);
            } else if (this.getExecuteParBag().getExecuteEnum() == ExecuteEnum.Delete
                    || this.getExecuteParBag().getExecuteEnum() == ExecuteEnum.Update) {
                affectedCount = DataAccess.executeScalarToUpdateOrDeleteOnlyReturnAff(this);
            } else {
                affectedCount = DataAccess.executeNonQuery(this);
            }
            if (affectedCount == -1L) {
                if (ex != null) {
                    ex.printStackTrace();
                    BitterLogUtil.getInstance().error(ex.getMessage());
                }
            }
        } catch (Exception exx) {
            BitterLogUtil.getInstance().error(exx.getMessage());
        }finally {
            this.dispose();
        }

        return affectedCount;
    }

    public void addInScope(List<BaseQuery> list) {
        list.add(this);
    }
}
