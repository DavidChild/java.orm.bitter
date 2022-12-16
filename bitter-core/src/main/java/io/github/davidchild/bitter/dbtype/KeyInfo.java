package io.github.davidchild.bitter.dbtype;

import java.lang.reflect.Field;

import com.baomidou.mybatisplus.annotation.IdType;

import io.github.davidchild.bitter.tools.CoreStringUtils;

public class KeyInfo {

    private final String dbFieldName;
    private final IdType dbIdType;
    private final String fieldName;
    private Field field;
    private Object value;

    public KeyInfo(String fieldName, String dbFieldName, IdType dBIdType) {

        this.dbFieldName = dbFieldName;
        this.dbIdType = dBIdType;
        this.fieldName = fieldName;
    }

    public String getDbFieldName() {
        return CoreStringUtils.isNotEmpty(this.dbFieldName) ? this.dbFieldName : this.fieldName;
    }

    public IdType getDbIdType() {
        return dbIdType;
    }

    public String getFieldName() {
        return fieldName;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

}
