package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.connection.DataAccess;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.exception.DbException;

import java.util.List;

public class DqlQuery extends  BaseQuery{
    protected DataTable getData() {
        try {
            this.convert();
            DataTable dt = DataAccess.executeQuery(this);
            return dt;
        } catch (DbException ex) {
            throw ex;
        } finally {
            this.disposeForData();
        }
    }

    protected <T extends BaseModel> List<T> getDataList() {
        this.convert();
        return DataAccess.executeQueryReturnDataList(this);
    }
}
