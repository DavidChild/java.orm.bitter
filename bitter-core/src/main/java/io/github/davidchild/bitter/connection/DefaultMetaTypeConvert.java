package io.github.davidchild.bitter.connection;

import java.lang.reflect.Field;

public class DefaultMetaTypeConvert implements IMetaTypeConvert {
    @Override
    public Object convertMetaTypeToJavaTypeValue(Field field, Object dbValue) {
        return dbValue;
    }

    @Override
    public Object convertJavaTypeToMetaTypeValue(Field field, Object javaTypeValue) {
        return javaTypeValue;
    }
}
