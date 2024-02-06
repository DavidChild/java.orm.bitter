package io.github.davidchild.bitter.dbtype;

import java.lang.reflect.Type;

public class MetaTypeCt {

    private static final MetaTypeLoaderWrapper metaTypeLoaderWrapper = new MetaTypeLoaderWrapper();

    private static final MetaTypeConvertRegistry metaTypeConvertRegistry = new MetaTypeConvertRegistry();

    private MetaTypeCt() {

    }

    public static  Class<?> getClassForMetaName(String name) throws ClassNotFoundException {
        return metaTypeLoaderWrapper.classForName(name);
    }


    public static   <T> TypeHandlerBase<T> getTypeHandler(Class<T> type) {
        return metaTypeConvertRegistry.getTypeHandler((Type) type, null);
    }

    public static   <T> TypeHandlerBase<T> getTypeHandler(Type type) {
        return metaTypeConvertRegistry.getTypeHandler((Type) type, null);
    }


}
