package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.excutequery.BuildParams;
import io.github.davidchild.bitter.excutequery.ClickHouseQuery;
import io.github.davidchild.bitter.excutequery.MySqlQuery;
import io.github.davidchild.bitter.parbag.IScopeBag;
import io.github.davidchild.bitter.tools.BitterLogUtil;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseQuery extends BaseExecute implements Type {

    protected  RunnerParam  insertCommandText(boolean IsOutIdentity,BuildParams buildParams){ return  null;};

    protected  <T extends BaseModel> RunnerParam deleteCommandText(BuildParams buildParams){ return  null;};

    protected  <T extends BaseModel> RunnerParam updateCommandText(BuildParams buildParams){ return  null;};

    protected  RunnerParam selectCommandText(BuildParams buildParams){ return  null;};

    protected  RunnerParam querySelectCommandText(BuildParams buildParams){ return  null;};

    protected  RunnerParam countCommandText(BuildParams buildParams){ return  null;};

    protected  RunnerParam executeCommandText(BuildParams buildParams){ return  null;};

    protected  RunnerParam pageCommandText(BuildParams buildParams){ return  null;};

    protected   RunnerParam pageCountText(BuildParams buildParams){ return  null;};

    protected  RunnerParam bachInsert(BuildParams buildParams){ return  null;};

    protected  List<RunnerParam> createScope(){
        List<RunnerParam> runnerParamList = new ArrayList<>();
        List<BaseQuery> list = ((IScopeBag)this.getSingleQuery().getBagOp()).getScopeList();
        if (list == null && list.size() <1) {
            return null;
        }
        for (BaseQuery q : list) {
            q.convert();
            RunnerParam runnerParam = q.setCommandText();
            runnerParamList.add(runnerParam);
        }
        return  runnerParamList;
    }


    // create sub statement for in or other sql
    public MonitorInfo getMonitor() {
        MonitorInfo info = new MonitorInfo();
        return info;
    }

    protected RunnerParam convert() {
        try {
            BaseQuery qQuery = this.mapToExecuteQuery();
            RunnerParam  runnerParam = qQuery.setCommandText();
            return  runnerParam;
        } catch(Exception ex) {
            BitterLogUtil.getInstance().error("baseQuery.convert error:" + ex.getMessage());
            throw ex;
        }
    }

    private BaseQuery mapToExecuteQuery() {
        BaseQuery vdb;
        switch (this.getSingleQuery().getBagOp().getDbType()) {
            case ClickHouse:
                vdb = new ClickHouseQuery();
                break;
            default:
                vdb = new MySqlQuery();
                break;
        }
        vdb.setQuery(this.getSingleQuery());
        return vdb;
    }

    protected   RunnerParam setCommandText() {
        RunnerParam runnerParam = null;
        BuildParams buildParams  = new BuildParams(this.getSingleQuery());
        switch (this.getSingleQuery().getBagOp().getBehaviorEnum()) {
            case PageQuery:
                runnerParam = pageCommandText(buildParams);
                break;
            case ExecuteQuery:
                runnerParam = executeCommandText(buildParams);
                break;
            case ExecuteQuerySelect:
                runnerParam = querySelectCommandText(buildParams);
                break;
            case Delete:
                runnerParam = deleteCommandText(buildParams);
                break;
            case Select:
                runnerParam = selectCommandText(buildParams);
                break;
            case Update:
                runnerParam = updateCommandText(buildParams);
                break;
            case Insert:
                runnerParam = insertCommandText(false,buildParams);
                break;
            case Count:
                runnerParam = countCommandText(buildParams);
                break;
            case PageCount:
                runnerParam = pageCountText(buildParams);
                break;
            case BachInsert:
                runnerParam = bachInsert(buildParams);
                break;
            default:
                break;
        }
        return runnerParam;

    }
}
