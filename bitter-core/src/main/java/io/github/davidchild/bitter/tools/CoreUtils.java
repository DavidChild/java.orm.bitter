package io.github.davidchild.bitter.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.dbtype.FieldProperty;
import io.github.davidchild.bitter.dbtype.KeyInfo;
import io.github.davidchild.bitter.exception.DbException;

public class CoreUtils {

    /**
     * description 获取实体的db层的主键信息
     * 
     * @param type 类型
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static <T> KeyInfo getPrimaryField(Class<?> type, T data) {
        Field[] fields = type.getDeclaredFields();
        KeyInfo ki = null;
        String tableName = "";
        for (Field field : fields) {
            // 是否引用ApiModelProperty注解
            boolean bool = field.isAnnotationPresent(TableId.class);
            if (bool) {
                TableId tableId = field.getAnnotation(TableId.class);
                ki = new KeyInfo(field.getName(), tableId.value(), tableId.type());
                ki.setField(field);
                if (data != null) {
                    Object value = getFieldValueByName(field.getName(), data);
                    ki.setValue(value);
                }
                break;
            }
        }
        return ki;
    }

    /**
     * description 获取实体的所有字段，和对应注解值
     * 
     * @param object 实体
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static <T> String getTableNameByObject(T object) {
        Class<?> type = object.getClass();
        return getTableNameByType(type);
    }

    /**
     * description 获取实体的所有字段，和对应注解值
     * 
     * @param type 类型
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static <T> List<FieldProperty> getFieldsByType(Class<?> type, T data) {
        Field[] fields = type.getDeclaredFields();
        List<FieldProperty> resultMap = new ArrayList<>();
        Arrays.asList(fields).forEach(item -> {
            boolean bool = item.isAnnotationPresent(TableField.class);
            if (bool) {
                TableField tableField = item.getAnnotation(TableField.class);
                FieldProperty filedProperty = new FieldProperty();
                filedProperty.setFieldName(tableField.value());
                filedProperty.setType(item.getType());
                filedProperty.setField(item);

                if (tableField.jdbcType() != null) {
                    filedProperty.setDbType(tableField.jdbcType());
                }
                if (data != null) {
                    Object value = getFieldValueByName(item.getName(), data);
                    filedProperty.setValue(value);
                }
                filedProperty.setClassInnerFieldName(item.getName());
                resultMap.add(filedProperty);
            }
        });
        return resultMap;
    }

    /**
     * description 获取实体的所有字段，和对应注解值(除去主键）
     * 
     * @param object 类型
     * @param object 数据
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static <T> List<FieldProperty> getFields(Class<?> type, T object) {
        return getFieldsByType(type, object);

    }

    /**
     * description 获取实体的所有字段，和对应注解值(包含主键）
     * 
     * @param object 类型
     * @param object 数据
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static <T> List<FieldProperty> getAllFields(Class<?> type, T object) {
        List<FieldProperty> lp = getFields(type, object);
        KeyInfo p = getPrimaryField(type, object);
        if (lp == null) {
            lp = new ArrayList<>();
        }
        if (p != null) {
            FieldProperty fp = new FieldProperty();
            fp.setFieldName(p.getDbFieldName());
            fp.setClassInnerFieldName(p.getFieldName());
            fp.isKey = true;
            fp.setValue(p.getValue());
            fp.isIdentity = p.getDbIdType() == IdType.AUTO ? true : false;
            fp.setIdType(p.getDbIdType());
            fp.setField(p.getField());
            lp.add(fp);
        }
        return lp;
    }

    /**
     * description 获取表名
     * 
     * @param type 类型
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static <T> String getTableNameByType(Class<T> type) {
        TableName annotation = type.getDeclaredAnnotation(TableName.class);
        if (annotation == null) {
            return "";
        }
        return annotation.value();
    }

    private static Object getFieldValueByName(String fieldName, Object o) throws DbException {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[] {});
            Object value = method.invoke(o, new Object[] {});
            return value;
        } catch (Exception e) {
            throw new DbException(e.getMessage());
        }
    }

    /**
     * description 获取当前类名
     * 
     * @param type 类型
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static String GetClassShortName(Class<? extends BaseModel> type) {
        return type.getName();
    }
}
