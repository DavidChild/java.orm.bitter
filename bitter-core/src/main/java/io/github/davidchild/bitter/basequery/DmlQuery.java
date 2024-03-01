package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.connection.DataAccess;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.ModelException;
import io.github.davidchild.bitter.parbag.IBachInsertBag;
import io.github.davidchild.bitter.parbag.IBagOp;
import io.github.davidchild.bitter.parbag.IScopeBag;
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
        IBagOp queryRunnerInfo = getSingleQuery().getBagOp();
        ExecuteEnum executeEnum = queryRunnerInfo.getBehaviorEnum();
        RunnerParam runnerParam = null;
        List<RunnerParam> runnerParamList = null;
        if(executeEnum == ExecuteEnum.BachInsert){
            if(((IBachInsertBag)(queryRunnerInfo)).getBachData() == null ||  ((IBachInsertBag)(queryRunnerInfo)).getBachData().size()<1) return 1l;
        }
        if(executeEnum == ExecuteEnum.Scope){
            if(!((IScopeBag)(queryRunnerInfo)).getScopeResult()) return  -1l;
            if(((IScopeBag)(queryRunnerInfo)).getScopeList() == null ||  ((IScopeBag)(queryRunnerInfo)).getScopeList().size()<1) return 1l;
        }

        if ((executeEnum == ExecuteEnum.Delete || executeEnum == ExecuteEnum.Update) && queryRunnerInfo.getData() != null) {
            DataValue key = CoreUtils.getTypeKey(queryRunnerInfo.getModelType(), queryRunnerInfo.getData());
            if (key == null)
                throw new ModelException("bitter checked the model entity ( " + queryRunnerInfo.getModelType().getName() + " ) have not the tableId annotation. ple set  the \" @TableId \"  annotation for the database model class.");
        }
        try {
            if (executeEnum == ExecuteEnum.Scope) {
                runnerParamList = this.createScope();
                if(runnerParamList == null || runnerParamList.size() < 1){
                    return  1l;
                }

            } else {
                runnerParam = convert();
                runnerParam.setMode(queryRunnerInfo.getExecuteMode());
                if(runnerParam == null ){
                    return  1l;
                }
            }
        } catch (Exception exx) {
            BitterLogUtil.getInstance().error("bitter-executor-convert-error:" + exx.getMessage());
            return -1L;
        }
        long affectedCount = -1L;
        try {
            if (executeEnum == ExecuteEnum.Insert) {
                long aff = DataAccess.executeScalarToWriterOnlyForInsertReturnIdentity(runnerParam);
                if (aff == -1L)
                    return aff;
                if (!(queryRunnerInfo.getIsOutIdentity())) {
                    return 1L;
                }
                affectedCount = aff;
            } else if (executeEnum == ExecuteEnum.BachInsert) {

                affectedCount = DataAccess.executeInsertBach(runnerParam);
            } else if (executeEnum == ExecuteEnum.Scope) {
                if (!(((IScopeBag)queryRunnerInfo).getScopeResult()))
                    return -1L;
                affectedCount = DataAccess.executeScope(runnerParamList,queryRunnerInfo.getExecuteMode());
            } else if (executeEnum == ExecuteEnum.Delete
                    || executeEnum == ExecuteEnum.Update) {
                affectedCount = DataAccess.executeScalarToUpdateOrDeleteOnlyReturnAff(runnerParam);
            } else {
                affectedCount = DataAccess.executeNonQuery(runnerParam);
            }
            if (affectedCount == -1L) {
                if (ex != null) {
                    ex.printStackTrace();
                    BitterLogUtil.getInstance().error(ex.getMessage());
                }
            }
        } catch (Exception exx) {
            BitterLogUtil.getInstance().error(exx.getMessage());
        } finally {
            if(queryRunnerInfo != null){
                queryRunnerInfo.dispose();
                runnerParam = null; //优化内存
            }
        }

        return affectedCount;
    }

    public void addInScope(List<BaseQuery> list) {
        list.add(this);
    }
}
