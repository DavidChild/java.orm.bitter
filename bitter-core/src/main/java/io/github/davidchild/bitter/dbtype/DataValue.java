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


//    public Object getNullDefaultValue(){
//        TypeHandlerBase<?>  handler = MetaTypeCt.getTypeHandler(this.classType);
//    }

    public <T> DataValue  setDataValue(FieldProperty property, T data) {
        this.setDbIdType(property.getIdType());
        this.setClassType(property.getField().getType());
        this.setDbName(property.getDbFieldName());
        this.setClassInnerName(property.getClassInnerFieldName());
        this.setIsKey(property.isKey);
        this.setIsIdentity(property.isIdentity);
        if (data != null) {
            Object v = CoreUtils.getFieldValueByName(property.getClassInnerFieldName(), data);
            this.setValue(v);
        }
        return  this;
    }
}
