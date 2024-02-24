package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.connection.DataAccess;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.ModelException;
import io.github.davidchild.bitter.excutequery.ClickHouseQuery;
import io.github.davidchild.bitter.excutequery.MySqlQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagInsert;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreUtils;

import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.List;

public class BaseQuery extends BaseExecute implements Type {

    protected DataTable getData() throws SQLException {
        this.convert();
        DataTable mapData = DataAccess.executeQuery(this);
        if (mapData != null && mapData.size() > 0) {
            return mapData;
        }
        return null;
    }


    protected <T extends BaseModel> List<T> getDataList() {
        this.convert();
        return DataAccess.executeQueryReturnDataList(this);
    }

    public void addInScope(List<BaseQuery> list) {
        list.add(this);
    }

    protected void insertCommandText(boolean IsOutIdentity) {
        clearParameters();
    }

    protected void deleteCommandText() {
        clearParameters();
    }

    protected void updateCommandText() {
        clearParameters();
    }

    protected void selectCommandText()  {
        clearParameters();
    }

    protected void countCommandText() {
        clearParameters();
    }

    protected void executeCommandText() {
        clearParameters();
    }

    protected void pageCommandText() {
        clearParameters();
    }

    protected void pageCountText() {
        clearParameters();
    }

    protected void bachInsert() {
        clearParameters();
    }

    protected void createScope() {
        clearParameters();
    }


    // create sub statement for in or other sql
    protected SubWhereStatement createSubWhereStatement() {
        return new SubWhereStatement(this);
    }

    protected SubColumnStatement createSubColumnStatement() {
        return new SubColumnStatement(this);
    }

    protected SubOrderStatement createOrderStatement() {
        return new SubOrderStatement(this);
    }
    protected SubUpdateColumnStatement createUpdateColumnStatement() {
        return new SubUpdateColumnStatement(this);
    }

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
        if((this.executeParBag.getExecuteEnum() == ExecuteEnum.Delete || this.executeParBag.getExecuteEnum() == ExecuteEnum.Update) && this.getExecuteParBag().getData() != null){
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
            if (this.executeParBag.getExecuteEnum() == ExecuteEnum.Insert) {
                long aff = DataAccess.executeScalarToWriterOnlyForInsertReturnIdentity(this);
                if (aff == -1L)
                    return aff;
                if (!((ExecuteParBagInsert) this.executeParBag).isOutIdentity()) {
                    return 1L;
                }
                affectedCount = aff;
            } else if (this.executeParBag.getExecuteEnum() == ExecuteEnum.BachInsert) {
                affectedCount = DataAccess.executeInsertBach(this);
            } else if (this.executeParBag.getExecuteEnum() == ExecuteEnum.Scope) {
                if (!this.scopeSuccess)
                    return -1L;
                affectedCount = DataAccess.executeScope(this);
            } else if (this.executeParBag.getExecuteEnum() == ExecuteEnum.Delete
                    || this.executeParBag.getExecuteEnum() == ExecuteEnum.Update) {
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
        }
        return affectedCount;
    }

    public MonitorInfo toMonitorInfo() {
        convert();
        MonitorInfo info = new MonitorInfo();
        info.setCommandSqlText(this.commandText);
        info.setParameters(this.parameters);
        return info;
    }

    public void convert() {
        try {
            BaseQuery qQuery = this.mapToExecuteQuery();
            qQuery.setCommandText();
            this.setParameters(qQuery.getParameters());
            this.commandText = qQuery.commandText;
            this.scopeSuccess = qQuery.scopeSuccess;
            this.setScopeCommands(qQuery.getScopeCommands());
            this.setScopeParams(qQuery.getScopeParams());
        } catch (Exception ex) {
            BitterLogUtil.getInstance().error("baseQuery.convert error:" + ex.getMessage());
            throw ex;
        }
    }
    private BaseQuery mapToExecuteQuery() {
        BaseQuery vdb;
        switch (this.getDbType()) {
            case ClickHouse:
                vdb = new ClickHouseQuery();
                break;
            default:
                vdb = new MySqlQuery();
                break;
        }
        vdb.executeParBag = this.executeParBag;
        vdb.setDbType(this.getDbType());
        vdb.setCommandText(this.getCommandText());
        vdb.setParameters( this.getParameters());
        return vdb;
    }

    private void setCommandText() {
        switch (this.getExecuteParBag().getExecuteEnum()) {
            case PageQuery:
                pageCommandText();
                break;
            case ExecuteQuery:
                executeCommandText();
                break;
            case Delete:
                deleteCommandText();
                break;
            case Select:
                selectCommandText();
                break;
            case Update:
                updateCommandText();
                break;
            case Insert:
                insertCommandText(false);
                break;
            case Count:
                countCommandText();
                break;
            case PageCount:
                pageCountText();
                break;
            case BachInsert:
                bachInsert();
                break;
            case Scope:
                createScope();
                break;
            default:
                break;
        }
    }
}
