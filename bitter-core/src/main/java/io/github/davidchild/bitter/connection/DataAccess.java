package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.datatable.DataTable;

import java.util.List;

public class DataAccess {

    DataAccess() {}
    public static Long executeScalarToWriterOnlyForInsertReturnIdentity(BaseQuery baseQuery) {
        long id;
        IDbStatement statement = StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode());
        id = statement.Insert(baseQuery.getCommandText(), baseQuery.getParameters(),
            baseQuery.getExecuteParBag().getIsIdentity());
        return id;
    }

    public static Long executeScalarToUpdateOrDeleteOnlyReturnAff(BaseQuery baseQuery) {
        long aff;
        IDbStatement statement = StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode());
        aff = statement.update(baseQuery.getCommandText(), baseQuery.getParameters());
        return aff;
    }

    public static Long executeNonQuery(BaseQuery baseQuery) {
        long isSuccess;
        IDbStatement statement = StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode());
        isSuccess = statement.execute(baseQuery.getCommandText(), baseQuery.getParameters());
        return isSuccess;
    }

    public static Long executeInsertBach(BaseQuery baseQuery) {
        long isSuccess;
        IDbStatement statement = StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode());
        isSuccess = statement.execute(baseQuery.getCommandText(), baseQuery.getParameters());
        return isSuccess;
    }

    public static Long executeScope(BaseQuery baseQuery) {
        long isSuccess;
        IDbStatement statement = StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode());
        isSuccess = statement.executeScope(baseQuery.getScopeCommands(), baseQuery.getScopeParams());
        return isSuccess;
    }

    public static DataTable executeQuery(BaseQuery baseQuery) {
        IMetaTypeConvert convert = StatementFactory.getMetaTyeConvert(baseQuery.getDbType());
        return (DataTable) StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode())
                .Query(baseQuery.getCommandText(), baseQuery.getParameters(),new JavaBeanMapResultHandler(convert,null));
    }

    public static <T extends BaseModel> T executeQueryReturnSingleData(BaseQuery baseQuery) {
        IMetaTypeConvert convert = StatementFactory.getMetaTyeConvert(baseQuery.getDbType());
        T t = (T) StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode())
                .Query(baseQuery.getCommandText(), baseQuery.getParameters(), new SingleModelResultHandler(convert,baseQuery.getExecuteParBag().getModelType()));
        return t;

    }
    public static <T extends BaseModel> List<T> executeQueryReturnDataList(BaseQuery baseQuery) {
        IMetaTypeConvert convert = StatementFactory.getMetaTyeConvert(baseQuery.getDbType());
        List<T> lt = (List<T>) StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode())
                .Query(baseQuery.getCommandText(), baseQuery.getParameters(), new ModesResultHandler(convert,baseQuery.getExecuteParBag().getModelType()));
        return lt;

    }

}
