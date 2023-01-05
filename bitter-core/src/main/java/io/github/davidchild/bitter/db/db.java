package io.github.davidchild.bitter.db;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.delete.Delete;
import io.github.davidchild.bitter.op.excute.ExecuteInSql;
import io.github.davidchild.bitter.op.find.FindQuery;
import io.github.davidchild.bitter.op.insert.BachInsert;
import io.github.davidchild.bitter.op.page.PageQuery;
import io.github.davidchild.bitter.op.read.ExecuteQuery;
import io.github.davidchild.bitter.op.read.QueryPrimaryKey;
import io.github.davidchild.bitter.op.scope.DbScope;
import io.github.davidchild.bitter.op.update.Update;

public class db {
//    public static DoResult doSet(DataSourceType targetDb, Function<?, ?> fn) {
//        TargetAction action = new TargetAction();
//        return action.DoSet(targetDb, fn);
//
//    }
//
//    public static DoResult doSet(String targetDb, Function<?, ?> fn) {
//        TargetAction action = new TargetAction();
//        return action.DoSet(targetDb, fn);
//    }

    public static <T extends BaseModel> FindQuery<T> findQuery(Class<T> clazz) {
        return new FindQuery<>(clazz);
    }

    public static <T extends BaseModel> Delete<T> delete(Class<T> clazz) {
        return new Delete<>(clazz);
    }

    public static <T extends BaseModel> Update<T> update(Class<T> clazz) {
        return new Update<>(clazz);
    }

    public static ExecuteQuery findQuery(String commandText, Object... params) {
        return new ExecuteQuery(commandText, params);
    }

    public static <T extends BaseModel> BachInsert bachInsert() {
        return new BachInsert();
    }

    public static DbScope doScope() {
        return new DbScope();
    }

    public static <T extends BaseModel> QueryPrimaryKey findQuery(Class<T> clazz, Object id) {
        return new QueryPrimaryKey(clazz, id);
    }

    public static ExecuteInSql execute(String commandText, Object... params) {
        return new ExecuteInSql(commandText, params);
    }

    public static PageQuery pageQuery(String selectCommandText) {
        return new PageQuery(selectCommandText);
    }

}
