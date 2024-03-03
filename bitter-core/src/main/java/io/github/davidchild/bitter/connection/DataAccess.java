package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.datatable.DataTable;

import java.util.List;

public class DataAccess {

    DataAccess() {}
    public static Long executeScalarToWriterOnlyForInsertReturnIdentity(RunnerParam runnerParam) {
        long id;
        IDbStatement statement = StatementFactory.getStatement(runnerParam.getMode());
        id = statement.Insert(runnerParam);
        return id;
    }

    public static Long executeScalarToUpdateOrDeleteOnlyReturnAff(RunnerParam runnerParam) {
        long aff;
        IDbStatement statement = StatementFactory.getStatement(runnerParam.getMode());
        aff = statement.update(runnerParam);
        return aff;
    }

    public static Long executeNonQuery(RunnerParam runnerParam) {
        long isSuccess;
        IDbStatement statement = StatementFactory.getStatement(runnerParam.getMode());
        isSuccess = statement.execute(runnerParam);
        return isSuccess;
    }

    public static Long executeInsertBach(RunnerParam runnerParam) {
        long isSuccess;
        IDbStatement statement = StatementFactory.getStatement(runnerParam.getMode());
        isSuccess = statement.execute(runnerParam);
        return isSuccess;
    }

    public static Long executeScope(List<RunnerParam> runnerParam,ExecuteMode mode) {
        long isSuccess;
        IDbStatement statement = StatementFactory.getStatement(mode);
        isSuccess = statement.executeScope(runnerParam);
        return isSuccess;
    }

    public static DataTable executeQuery(RunnerParam runnerParam,DatabaseType databaseType) {
        IMetaTypeConvert convert = StatementFactory.getMetaTyeConvert(databaseType);
        return (DataTable) StatementFactory.getStatement(runnerParam.getMode())
                .Query(runnerParam, new JavaBeanMapResultHandler(convert,null));

    }

    public static <T extends BaseModel> T executeQueryReturnSingleData(RunnerParam runnerParam, DatabaseType databaseType, Class<?> modelType ) {
        IMetaTypeConvert convert = StatementFactory.getMetaTyeConvert(databaseType);
        T t = (T) StatementFactory.getStatement(runnerParam.getMode())
                .Query(runnerParam, new SingleModelResultHandler(convert,modelType));
        return t;

    }
    public static <T extends BaseModel> List<T> executeQueryReturnDataList(RunnerParam runnerParam,DatabaseType databaseType,Class<?> modelType) {
        IMetaTypeConvert convert = StatementFactory.getMetaTyeConvert(databaseType);
        List<T> lt = (List<T>) StatementFactory.getStatement(runnerParam.getMode())
                .Query(runnerParam, new ModesResultHandler(convert,modelType));
        return lt;

    }

}
