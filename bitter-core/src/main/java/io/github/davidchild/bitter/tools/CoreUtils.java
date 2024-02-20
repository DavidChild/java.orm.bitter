package io.github.davidchild.bitter.tools;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.CacheModelDefine;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.dbtype.FieldProperty;
import io.github.davidchild.bitter.dbtype.KeyInfo;
import io.github.davidchild.bitter.dbtype.MetaType;
import io.github.davidchild.bitter.exception.DbException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CoreUtils {

    /**
     * description 获取实体的db层的主键信息
     *
     * @param type 类型
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    private static <T> KeyInfo getPrimaryField(Class<?> type) {
        Field[] fields = type.getDeclaredFields();
        KeyInfo ki = null;
        String tableName = "";
        for (Field field : fields) {
            boolean bool = field.isAnnotationPresent(TableId.class);
            if (bool) {
                TableId tableId = field.getAnnotation(TableId.class);
                ki = new KeyInfo(field.getName(), tableId.value(), tableId.type());
                ki.setField(field);
                break;
            }
        }
        return ki;
    }

    public static <T>  DataValue getTypeKey(Class<?> type, T data){
        List<FieldProperty> list = getAllFields(type);
        return list.stream().filter(property -> property.isKey).map(item
                ->CoreUtils.getKeyDataValue(item,data)).findFirst().get();

    }

    public static <T> List<DataValue> getIdentity(Class<?> type, T data){
        List<FieldProperty> list = getAllFields(type);
        return  list.stream().filter(property -> property.isIdentity).map(item
                ->CoreUtils.getKeyDataValue(item,data)).collect(Collectors.toList());

    }

    private static <T> DataValue   getKeyDataValue(FieldProperty p, T data){
        DataValue dv = new DataValue();
        dv = dv.setDataValue(p,data);
        return  dv;
    }

    public static <T> List<DataValue> getTypeRelationData(Class<?> type, T data) {
        List<FieldProperty> list = getAllFields(type);
        List<DataValue> list_data = new ArrayList<>();
        if (list != null ) {
            for (FieldProperty field : list) {
                DataValue dv = new DataValue();
                list_data.add(dv.setDataValue(field,data));
            }
        }
        return  list_data;
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
    public static <T> List<FieldProperty> getFieldsByType(Class<?> type) {
        Field[] fields = type.getDeclaredFields();
        List<FieldProperty> resultMap = new ArrayList<>();
        for(Field item: fields){
            if(Modifier.isStatic(item.getModifiers())||Modifier.isFinal(item.getModifiers())) continue ;
            FieldProperty filedProperty = new FieldProperty();
            boolean bool_key = item.isAnnotationPresent(TableId.class);
            if(bool_key)continue;
            boolean bool = item.isAnnotationPresent(TableField.class);
            if(bool){
                TableField tableField = item.getAnnotation(TableField.class);
                if(!tableField.exist()) continue;
                if( tableField.value()!=null || tableField.value() != ""){
                    filedProperty.setFieldName(tableField.value().trim());
                }
                if (tableField.jdbcType() != null) {
                    filedProperty.setMetaType(MetaType.valueOf(tableField.jdbcType().name())); // 使用ibatis的tableField 属性
                }
            }
            if(filedProperty.getFieldName()==null||filedProperty.getFieldName() == ""){
                filedProperty.setFieldName(toDbField(item.getName()));
            }
            filedProperty.setType(item.getType());
            filedProperty.setField(item);
            filedProperty.setClassInnerFieldName(item.getName());
            resultMap.add(filedProperty);

        }
        return resultMap;
    }


    /**
     * 实体字段转换为数据库字段
     *
     * @param entityField 实体字段
     * @return
     */
    private static String toDbField(String entityField) {
        String dbField = "";
        StringBuilder sb = new StringBuilder();
        sb.append(entityField);
        char[] chars = entityField.toCharArray();
        int num = 0, index = 0;
        for (char aChar : chars) {
            if (Character.isUpperCase(aChar)) {
                int i1 = index + num;
                sb.replace(i1, i1 + 1, "_" + String.valueOf(Character.toLowerCase(aChar)));
                num++;
            }
            index++;
        }
        dbField = sb.toString();
        return dbField;
    }


    /**
     * description 获取实体的所有字段，和对应注解值(除去主键）
     *
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static <T> List<FieldProperty> getFields(Class<?> type) {
        return getFieldsByType(type);

    }

    /**
     * description 获取实体的所有字段，和对应注解值(包含主键）
     *
     * @author davidchild
     * @version 1.0
     * @date 2022/8/15 15:47
     */
    public static <T> List<FieldProperty> getAllFields(Class<?> type) {
        List<FieldProperty> cachedList  = CacheModelDefine.getCacheModelDefine().get(type.getTypeName());
        if(cachedList  != null) return cachedList;
        List<FieldProperty> lp = getFields(type);
        KeyInfo p = getPrimaryField(type);
        if (lp == null) {
            lp = new ArrayList<>();
        }
        if (p != null) {
            FieldProperty fp = new FieldProperty();
            fp.setFieldName(p.getDbFieldName());
            fp.setClassInnerFieldName(p.getFieldName().trim());
            fp.isKey = true;
            fp.setType(p.getField().getType());
            fp.setValue(p.getValue());
            fp.isIdentity = p.getDbIdType() == IdType.AUTO ? true : false;
            fp.setIdType(p.getDbIdType());
            fp.setField(p.getField());
            lp.add(fp);
        }
        if(lp != null) CacheModelDefine.getCacheModelDefine().putIfAbsent(type.getTypeName(),lp);
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

    public static Object getFieldValueByName(String fieldName, Object o) throws DbException {
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
