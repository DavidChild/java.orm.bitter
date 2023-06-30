package io.github.davidchild.bitter.connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class JavaBeanMapResultHandler extends  DataResultHandlerBase {

    public  JavaBeanMapResultHandler(IMetaTypeConvert convert,Class<?> targetClass){
        super(convert,null,ResultHandlerEnum.JavaBeanMap);
    }
    @Override
    public Object GetResult(ResultSet resultSet) throws SQLException {
        return convert.JavaBeanMapResult(resultSet);
    }
}
