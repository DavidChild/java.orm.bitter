package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.connection.Impl.IDbStatement;
import io.github.davidchild.bitter.connection.Impl.IIETyeConvert;
import io.github.davidchild.bitter.connection.Impl.MySqlTypeConvert;

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

    public static IIETyeConvert getIIETyeConvert(DatabaseType dbType) {
        IIETyeConvert convert;
        if (dbType == DatabaseType.MySQL) {
            convert = new MySqlTypeConvert();
        } else {
            convert = new MySqlTypeConvert();
            // todo: other impl convert; case:
        }
        return convert;
    }
}
