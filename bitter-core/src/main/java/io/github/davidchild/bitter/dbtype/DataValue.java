package io.github.davidchild.bitter.dbtype;

import com.baomidou.mybatisplus.annotation.IdType;
import io.github.davidchild.bitter.tools.CoreUtils;
import lombok.Data;

@Data
public class DataValue {
    private String dbName;
    private Object value;
    private String classInnerName;
    private Boolean isKey;
    private Boolean isIdentity;
    private  IdType dbIdType;
    private  Class<?> classType;

    public  DataValue(){

    }

    public <T> DataValue  setDataValue(FieldProperty property, T data) {
        DataValue dv = new DataValue();
        dv.setDbIdType(property.getIdType());
        dv.setClassType(property.getField().getType());
        dv.setDbName(property.getDbFieldName());
        dv.setClassInnerName(property.getClassInnerFieldName());
        dv.setIsKey(property.isKey);
        dv.setIsIdentity(property.isIdentity);
        if (data != null) {
            Object v = CoreUtils.getFieldValueByName(property.getClassInnerFieldName(), data);
            dv.setValue(v);
        }
        return dv;
    }
}
