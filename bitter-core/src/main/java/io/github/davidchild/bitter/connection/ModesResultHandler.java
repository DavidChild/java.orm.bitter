package io.github.davidchild.bitter.connection;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModesResultHandler extends  DataResultHandlerBase {


    public ModesResultHandler(IMetaTypeConvert convert,Class<?> targetclassType){
        super(convert,targetclassType,ResultHandlerEnum.Models);
    }
    @Override
    public Object GetResult(ResultSet resultSet) throws SQLException, InstantiationException, IllegalAccessException {
       return convert.ModelsResult(resultSet,targetType);
    }

}
