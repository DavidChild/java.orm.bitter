package io.github.davidchild.bitter.connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SingleModelResultHandler extends  DataResultHandlerBase {

    public SingleModelResultHandler(IMetaTypeConvert convert,Class<?> targetClass){
        super(convert,targetClass,ResultHandlerEnum.SingleModel);
    }
    @Override
    public Object GetResult(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
       return convert.SingleModelResult(resultSet,this.targetType);
    }
}
