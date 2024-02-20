package io.github.davidchild.bitter.connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class OriginMapResultHandler extends  DataResultHandlerBase {

    public OriginMapResultHandler(IMetaTypeConvert convert,Class<?> targetclassType){
        super(convert,targetclassType,ResultHandlerEnum.OriginMap);
    }
    @Override
    public Object GetResult(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        return  convert.OriginResult(resultSet);
    }
}
