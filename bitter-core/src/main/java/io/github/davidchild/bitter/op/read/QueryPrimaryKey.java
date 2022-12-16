package io.github.davidchild.bitter.op.read;

import java.sql.SQLException;

import io.github.davidchild.bitter.BaseModel;

public class QueryPrimaryKey {

    private ExecuteQuery query;
    private Object id;

    public <T extends BaseModel> QueryPrimaryKey(Class<? extends BaseModel> clazz, Object id) {
        query = new ExecuteQuery(clazz);
        this.setId(id);
    }

    public void setId(Object id) {
        this.id = id;
    }

    public <T extends BaseModel> T find() throws SQLException, InstantiationException, IllegalAccessException {
        T instance = query.queryById(id);
        return (T)instance;
    }
}
