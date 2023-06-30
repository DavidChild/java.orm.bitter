package io.github.davidchild.bitter.dbtype;

import io.github.davidchild.bitter.dbtype.typeImpl.*;
import org.apache.ibatis.type.TypeException;

import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.*;
import java.time.chrono.JapaneseDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class MetaTypeConvertRegistry {

    private final Map<MetaType, TypeHandlerBase<?>> metaTypeHandlerMap = new EnumMap<>(MetaType.class);
    private final Map<Type, Map<MetaType, TypeHandlerBase<?>>> typeHandlerMap = new ConcurrentHashMap<>();
    private final Map<Class<?>, TypeHandlerBase<?>> allTypeHandlersMap = new HashMap<>();

    private static final Map<MetaType, TypeHandlerBase<?>> NULL_TYPE_HANDLER_MAP = Collections.emptyMap();

    private Class<? extends TypeHanderDealBase> defaultEnumTypeHandler = EnumMetaTypeHandler.class;


    public MetaTypeConvertRegistry() {


        register(Boolean.class, new BooleanMetaTypeHandler());
        register(boolean.class, new BooleanMetaTypeHandler());
        register(MetaType.BOOLEAN, new BooleanMetaTypeHandler());
        register(MetaType.BIT, new BooleanMetaTypeHandler());

        register(Byte.class, new ByteMetaTypeHandler());
        register(byte.class, new ByteMetaTypeHandler());
        register(MetaType.TINYINT, new ByteMetaTypeHandler());

        register(Short.class, new ShortMetaTypeHandler());
        register(short.class, new ShortMetaTypeHandler());
        register(MetaType.SMALLINT, new ShortMetaTypeHandler());

        register(Integer.class, new IntegerMetaTypeHandler());
        register(int.class, new IntegerMetaTypeHandler());
        register(MetaType.INTEGER, new IntegerMetaTypeHandler());

        register(Long.class, new LongMetaTypeHandler());
        register(long.class, new LongMetaTypeHandler());

        register(Float.class, new FloatMetaTypeHandler());
        register(float.class, new FloatMetaTypeHandler());
        register(MetaType.FLOAT, new FloatMetaTypeHandler());

        register(Double.class, new DoubleMetaTypeHandler());
        register(double.class, new DoubleMetaTypeHandler());
        register(MetaType.DOUBLE, new DoubleMetaTypeHandler());

        register(Reader.class, new ClobReaderMetaTypeHandler());
        register(String.class, new StringMetaTypeHandler());
        register(String.class, MetaType.CHAR, new StringMetaTypeHandler());
        register(String.class, MetaType.CLOB, new ClobMetaTypeHandler());
        register(String.class, MetaType.VARCHAR, new StringMetaTypeHandler());
        register(String.class, MetaType.LONGVARCHAR, new StringMetaTypeHandler());
        register(String.class, MetaType.NVARCHAR, new NStringMetaTypeHandler());
        register(String.class, MetaType.NCHAR, new NStringMetaTypeHandler());
        register(String.class, MetaType.NCLOB, new NClobMetaTypeHandler());
        register(MetaType.CHAR, new StringMetaTypeHandler());
        register(MetaType.VARCHAR, new StringMetaTypeHandler());
        register(MetaType.CLOB, new ClobMetaTypeHandler());
        register(MetaType.LONGVARCHAR, new StringMetaTypeHandler());
        register(MetaType.NVARCHAR, new NStringMetaTypeHandler());
        register(MetaType.NCHAR, new NStringMetaTypeHandler());
        register(MetaType.NCLOB, new NClobMetaTypeHandler());

        register(Object.class, MetaType.ARRAY, new ArrayMetaTypeHandler());
        register(MetaType.ARRAY, new ArrayMetaTypeHandler());

        register(BigInteger.class, new BigIntegerMetaTypeHandler());
        register(MetaType.BIGINT, new LongMetaTypeHandler());

        register(BigDecimal.class, new BigDecimalMetaTypeHandler());
        register(MetaType.REAL, new BigDecimalMetaTypeHandler());
        register(MetaType.DECIMAL, new BigDecimalMetaTypeHandler());
        register(MetaType.NUMERIC, new BigDecimalMetaTypeHandler());

        register(InputStream.class, new BlobInputStreamMetaTypeHandler());
        register(Byte[].class, new ByteObjectArrayMetaTypeHandler());
        register(Byte[].class, MetaType.BLOB, new BlobByteObjectArrayMetaTypeHandler());
        register(Byte[].class, MetaType.LONGVARBINARY, new BlobByteObjectArrayMetaTypeHandler());
        register(byte[].class, new ByteArrayMetaTypeHandler());
        register(byte[].class, MetaType.BLOB, new BlobMetaTypeHandler());
        register(byte[].class, MetaType.LONGVARBINARY, new BlobMetaTypeHandler());
        register(MetaType.LONGVARBINARY, new BlobMetaTypeHandler());
        register(MetaType.BLOB, new BlobMetaTypeHandler());

        register(Object.class,  new UnknownMetaTypeHandler());
        register(Object.class, MetaType.OTHER, new UnknownMetaTypeHandler());
        register(MetaType.OTHER,  new UnknownMetaTypeHandler());

        register(Date.class, new DateMetaTypeHandler());
        register(Date.class, MetaType.DATE, new DateOnlyMetaTypeHandler());
        register(Date.class, MetaType.TIME, new TimeOnlyMetaTypeHandler());
        register(MetaType.TIMESTAMP, new DateMetaTypeHandler());
        register(MetaType.DATE, new DateOnlyMetaTypeHandler());
        register(MetaType.TIME, new TimeOnlyMetaTypeHandler());

        register(java.sql.Date.class, new SqlDateMetaTypeHandler());
        register(java.sql.Time.class, new SqlTimeMetaTypeHandler());
        register(java.sql.Timestamp.class, new SqlTimestampMetaTypeHandler());

        register(String.class, MetaType.SQLXML, new SqlxmlMetaTypeHandler());

        register(Instant.class, new InstantMetaTypeHandler());
        register(LocalDateTime.class, new LocalDateTimeMetaTypeHandler());
        register(LocalDate.class, new LocalDateMetaTypeHandler());
        register(LocalTime.class, new LocalTimeMetaTypeHandler());
        register(OffsetDateTime.class, new OffsetDateTimeMetaTypeHandler());
        register(OffsetTime.class, new OffsetTimeMetaTypeHandler());
        register(ZonedDateTime.class, new ZonedDateTimeMetaTypeHandler());
        register(Month.class, new MonthMetaTypeHandler());
        register(Year.class, new YearMetaTypeHandler());
        register(YearMonth.class, new YearMonthMetaTypeHandler());
        register(JapaneseDate.class, new JapaneseDateMetaTypeHandler());
        register(Character.class, new CharacterMetaTypeHandler());
        register(char.class, new CharacterMetaTypeHandler());
    }

    private Map<MetaType, TypeHandlerBase<?>> getJdbcHandlerMap(Type type) {
        Map<MetaType, TypeHandlerBase<?>> jdbcHandlerMap = typeHandlerMap.get(type);
        if (jdbcHandlerMap != null) {
            return NULL_TYPE_HANDLER_MAP.equals(jdbcHandlerMap) ? null : jdbcHandlerMap;
        }
        if (type instanceof Class) {
            Class<?> clazz = (Class<?>) type;
            if (Enum.class.isAssignableFrom(clazz)) {
                if (clazz.isAnonymousClass()) {
                    return getJdbcHandlerMap(clazz.getSuperclass());
                }
                jdbcHandlerMap = getJdbcHandlerMapForEnumInterfaces(clazz, clazz);
                if (jdbcHandlerMap == null) {
                    register(clazz, getInstance(clazz, defaultEnumTypeHandler));
                    return typeHandlerMap.get(clazz);
                }
            } else {
                jdbcHandlerMap = getJdbcHandlerMapForSuperclass(clazz);
            }
        }
        typeHandlerMap.put(type, jdbcHandlerMap == null ? NULL_TYPE_HANDLER_MAP : jdbcHandlerMap);
        return jdbcHandlerMap;
    }

    public <T> void register(Class<T> javaType, TypeHandlerBase<? extends T> typeHandler) {
        register((Type) javaType, typeHandler);
    }


    private <T> void register(Type javaType, TypeHandlerBase<? extends T> typeHandler) {
        MappedMetaTypes mappedJdbcTypes = typeHandler.getClass().getAnnotation(MappedMetaTypes.class);
        if (mappedJdbcTypes != null) {
            for (MetaType handledJdbcType : mappedJdbcTypes.value()) {
                register(javaType, handledJdbcType, typeHandler);
            }
            if (mappedJdbcTypes.includeNullJdbcType()) {
                register(javaType, null, typeHandler);
            }
        } else {
            register(javaType, null, typeHandler);
        }
    }


    private void register(Type javaType, MetaType metaType, TypeHandlerBase<?> handler) {
        if (javaType != null) {
            Map<MetaType, TypeHandlerBase<?>> map = typeHandlerMap.get(javaType);
            if (map == null || map == NULL_TYPE_HANDLER_MAP) {
                map = new HashMap<>();
            }
            map.put(metaType, handler);
            typeHandlerMap.put(javaType, map);
        }
        allTypeHandlersMap.put(handler.getClass(), handler);
    }
    public void register(MetaType jdbcType, TypeHandlerBase<?> handler) {
        metaTypeHandlerMap.put(jdbcType, handler);
    }

    private Map<MetaType, TypeHandlerBase<?>> getJdbcHandlerMapForSuperclass(Class<?> clazz) {
        Class<?> superclass = clazz.getSuperclass();
        if (superclass == null || Object.class.equals(superclass)) {
            return null;
        }
        Map<MetaType, TypeHandlerBase<?>> jdbcHandlerMap = typeHandlerMap.get(superclass);
        if (jdbcHandlerMap != null) {
            return jdbcHandlerMap;
        }
        return getJdbcHandlerMapForSuperclass(superclass);
    }

    private Map<MetaType, TypeHandlerBase<?>> getJdbcHandlerMapForEnumInterfaces(Class<?> clazz, Class<?> enumClazz) {
        for (Class<?> iface : clazz.getInterfaces()) {
            Map<MetaType, TypeHandlerBase<?>> jdbcHandlerMap = typeHandlerMap.get(iface);
            if (jdbcHandlerMap == null) {
                jdbcHandlerMap = getJdbcHandlerMapForEnumInterfaces(iface, enumClazz);
            }
            if (jdbcHandlerMap != null) {
                // Found a type handler registered to a super interface
                HashMap<MetaType, TypeHandlerBase<?>> newMap = new HashMap<>();
                for (Map.Entry<MetaType, TypeHandlerBase<?>> entry : jdbcHandlerMap.entrySet()) {
                    // Create a type handler instance with enum type as a constructor arg
                    newMap.put(entry.getKey(), getInstance(enumClazz, entry.getValue().getClass()));
                }
                return newMap;
            }
        }
        return null;
    }


    private  <T> TypeHandlerBase<T> getInstance(Class<?> javaTypeClass, Class<?> typeHandlerBaseClass) {
        if (javaTypeClass != null) {
            try {
                Constructor<?> c = typeHandlerBaseClass.getConstructor(Class.class);
                return (TypeHandlerBase<T>) c.newInstance(javaTypeClass);
            } catch (NoSuchMethodException ignored) {
                // ignored
            } catch (Exception e) {
                throw new TypeException("Failed invoking constructor for handler " + typeHandlerBaseClass, e);
            }
        }
        try {
            Constructor<?> c = typeHandlerBaseClass.getConstructor();
            return (TypeHandlerBase<T>) c.newInstance();
        } catch (Exception e) {
            throw new TypeException("Unable to find a usable constructor for " + typeHandlerBaseClass, e);
        }
    }


    private TypeHandlerBase<?> pickSoleHandler(Map<MetaType, TypeHandlerBase<?>> jdbcHandlerMap) {
        TypeHandlerBase<?> soleHandler = null;
        for (TypeHandlerBase<?> handler : jdbcHandlerMap.values()) {
            if (soleHandler == null) {
                soleHandler = handler;
            } else if (!handler.getClass().equals(soleHandler.getClass())) {
                // More than one type handlers registered.
                return null;
            }
        }
        return soleHandler;
    }

    public  <T> TypeHandlerBase<T> getTypeHandler(Type type, MetaType metaType) {
//        if (MapperMethod.ParamMap.class.equals(type)) {
//            return null;
//        }
        Map<MetaType, TypeHandlerBase<?>> jdbcHandlerMap = getJdbcHandlerMap(type);
        TypeHandlerBase<?> handler = null;
        if (jdbcHandlerMap != null) {
            handler = jdbcHandlerMap.get(metaType);
            if (handler == null) {
                handler = jdbcHandlerMap.get(null);
            }
            if (handler == null) {
                // #591
                handler = pickSoleHandler(jdbcHandlerMap);
            }
        }
        // type drives generics here
        return (TypeHandlerBase<T>) handler;
    }

}
