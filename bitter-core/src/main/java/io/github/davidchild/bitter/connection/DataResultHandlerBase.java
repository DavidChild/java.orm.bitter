package io.github.davidchild.bitter.connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class DataResultHandlerBase {

      IMetaTypeConvert convert;
      protected  DataResultHandlerBase(IMetaTypeConvert metaTypeConvert,Class<?> targetClass,ResultHandlerEnum resultHandlerEnum){
            targetType = targetClass;
            convert = metaTypeConvert;
            handlerEnum = resultHandlerEnum;

      }

      protected   Class<?> targetType;

      protected ResultHandlerEnum handlerEnum;

     public abstract  Object GetResult(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException;
}
