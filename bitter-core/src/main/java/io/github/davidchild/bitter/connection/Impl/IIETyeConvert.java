package io.github.davidchild.bitter.connection.Impl;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.davidchild.bitter.dbtype.FieldProperty;
import io.github.davidchild.bitter.exception.RefException;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreUtils;

public interface IIETyeConvert {

    Object convertDbToJavaTypeValue(Field field, Object dbValue);

    Object convertJavaToDbTypeValue(Field field, Object javaTypeValue);

    default List<Map<String, Object>> convertToMap(ResultSet rs) throws SQLException {
        List<Map<String, Object>> list = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        while (rs.next()) {
            Map<String, Object> rowData = new HashMap<>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i).toLowerCase(), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }

    default <T> List<T> listMap2JavaBean(List<Map<String, Object>> data, Class<T> targetClass) throws RefException {

        List<T> list;
        Object methodSetValue;
        try {

            list = new ArrayList<>();
            List<FieldProperty> fields = CoreUtils.getAllFields(targetClass, null);
            for (Map<String, Object> mapData : data) {
                T t = targetClass.newInstance();
                for (FieldProperty field : fields) {
                    String dbFieldName = field.getDbFieldName().toLowerCase();
                    try {
                        methodSetValue = mapData.get(dbFieldName);
                        if (methodSetValue != null) {
                            field.getField().setAccessible(true);
                            field.getField().set(t, convertDbToJavaTypeValue(field.getField(), methodSetValue));
                        }
                    } catch (SecurityException e) {
                        BitterLogUtil.getInstance().error(
                            "bitter-sqlData to java bean object -error,method-listMap2JavaBean:" + e.getMessage(), e);
                        throw new RuntimeException(e);
                    }

                }
                list.add(t);
            }
        } catch (InstantiationException e) {
            BitterLogUtil.getInstance()
                .error("bitter-sqlData to java bean object -error,method-listMap2JavaBean:" + e.getMessage(), e);
            throw new RefException("bitter: create targetClass fail", e);
        } catch (IllegalAccessException e) {
            BitterLogUtil.getInstance()
                .error("bitter-sqlData to java bean object -error,method-listMap2JavaBean:" + e.getMessage(), e);
            throw new RefException("bitter: convert db-->entity field type exception: ", e);
        }
        return list;
    }
}
