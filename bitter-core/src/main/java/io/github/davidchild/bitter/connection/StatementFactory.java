package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.basequery.ExecuteMode;

public class StatementFactory {

    public static IDbStatement getStatement(ExecuteMode statementEnum) {
        IDbStatement statement;
        if (statementEnum == ExecuteMode.NoCached) {
            statement = null; // TODO: Nocache 扩展
        } else {
            statement = new MysqlDbStatementCached();
        }
        return statement;

    }

    public static IMetaTypeConvert getMetaTyeConvert(DatabaseType dbType) {
        IMetaTypeConvert convert;
        if (dbType == DatabaseType.MySQL) {
            convert = new DefaultMetaTypeConvert();
        } else {
            convert = new DefaultMetaTypeConvert();
            // todo: other impl convert; case:
        }
        return convert;
    }
}
