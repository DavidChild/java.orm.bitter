package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.excutequery.ClickHouseQuery;
import io.github.davidchild.bitter.excutequery.MySqlQuery;
import io.github.davidchild.bitter.tools.BitterLogUtil;

import java.lang.reflect.Type;

public class BaseQuery extends BaseExecute implements Type {





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


    protected void querySelectCommandText()  {
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

    public MonitorInfo toMonitorInfo() {
        convert();
        MonitorInfo info = new MonitorInfo();
        info.setCommandSqlText(this.getCommandText());
        info.setParameters(this.getExecuteParBag().getDynamics());
        return info;
    }

     public void convert() {
        try {
            BaseQuery qQuery = this.mapToExecuteQuery();
            qQuery.setCommandText();
            this.setParameters(qQuery.getParameters());
            this.setCommandText(qQuery.getCommandText());
            this.setScopeSuccess(qQuery.isScopeSuccess());
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
        vdb.setExecuteParBag(this.getExecuteParBag());
        vdb.setDbType(this.getDbType());
        vdb.setCommandText(this.getCommandText());
        vdb.getExecuteParBag().setDynamics(this.getExecuteParBag().getDynamics());
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
            case ExecuteQuerySelect:
                querySelectCommandText();
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
