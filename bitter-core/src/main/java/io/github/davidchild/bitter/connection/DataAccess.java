package io.github.davidchild.bitter.connection;

import java.util.List;
import java.util.Map;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.connection.Impl.IDbStatement;
import io.github.davidchild.bitter.connection.Impl.IIETyeConvert;

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

    public static List<Map<String, Object>> executeQuery(BaseQuery baseQuery) {

        IIETyeConvert convert = StatementFactory.getIIETyeConvert(baseQuery.getDbType());
        return StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode())
            .queryMapFromDbData(baseQuery.getCommandText(), baseQuery.getParameters(), convert);

    }

    public static <T extends BaseModel> List<T> executeQueryReturnDataList(BaseQuery baseQuery) {

        IIETyeConvert convert = StatementFactory.getIIETyeConvert(baseQuery.getDbType());
        List<Map<String, Object>> lt = StatementFactory.getStatement(baseQuery.getExecuteParBag().getExecuteMode())
            .queryMapFromDbData(baseQuery.getCommandText(), baseQuery.getParameters(), convert);
        return (List<T>)convert.listMap2JavaBean(lt, baseQuery.getExecuteParBag().getModelType());

    }

}
