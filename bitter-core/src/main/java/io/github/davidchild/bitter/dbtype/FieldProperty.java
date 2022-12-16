package io.github.davidchild.bitter.dbtype;

import java.lang.reflect.Field;

import org.apache.ibatis.type.JdbcType;

import com.baomidou.mybatisplus.annotation.IdType;

import io.github.davidchild.bitter.tools.CoreStringUtils;

public class FieldProperty {

    public String fieldName;

    public Class<?> type;
    public boolean isIdentity = false;
    public JdbcType dbType;
    public Object value;
    public String classInnerFieldName;
    public boolean isKey = false;
    public boolean isNull = true;
    private IdType idType = IdType.NONE;
    private Field field;

    public void setFieldName(String filedName) {
        this.fieldName = filedName;
    }

    public Class<?> getType() {
        return type;
    }

    public void setType(Class<?> type) {
        this.type = type;
    }

    public String getClassInnerFieldName() {
        return classInnerFieldName;
    }

    public void setClassInnerFieldName(String classInnerFieldName) {
        this.classInnerFieldName = classInnerFieldName;
    }

    public boolean isKey() {
        return isKey;
    }

    public void setKey(boolean key) {
        isKey = key;
    }

    public boolean isNull() {
        return isNull;
    }

    public void setNull(boolean aNull) {
        isNull = aNull;
    }

    public void setDbType(JdbcType dbType) {
        this.dbType = dbType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getDbFieldName() {
        return CoreStringUtils.isNotEmpty(this.fieldName) ? this.fieldName : this.classInnerFieldName;
    }
}
