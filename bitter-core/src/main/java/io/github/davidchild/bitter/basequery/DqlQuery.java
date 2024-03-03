package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.connection.DataAccess;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.exception.DbException;

import java.util.List;

public class DqlQuery extends  BaseQuery {
    protected DataTable getData() {
        RunnerParam runnerParam = null;
        try {
            runnerParam = this.convert();
            DataTable dt = DataAccess.executeQuery(runnerParam, this.getSingleQuery().getBagOp().getDbType());
            return dt;
        } catch (DbException ex) {
            throw ex;
        } finally {
            runnerParam = null; //优化内存
        }
    }

    protected <T extends BaseModel> List<T> getDataList() {
        RunnerParam runnerParam = null;
        try {
            runnerParam = this.convert();
            return DataAccess.executeQueryReturnDataList(runnerParam, this.getSingleQuery().getBagOp().getDbType(), this.getSingleQuery().getBagOp().getModelType());
        } catch (DbException ex) {
            throw ex;
        } finally {
            runnerParam = null; //优化内存
        }
    }
}
