package io.github.davidchild.bitter.connection;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.datatable.DataColumn;
import io.github.davidchild.bitter.datatable.DataRow;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.dbtype.FieldProperty;
import io.github.davidchild.bitter.dbtype.MetaTypeCt;
import io.github.davidchild.bitter.dbtype.TypeHandlerBase;
import io.github.davidchild.bitter.tools.BitterLogUtil;
import io.github.davidchild.bitter.tools.CoreUtils;
import org.apache.ibatis.io.Resources;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public interface IMetaTypeConvert {

    Object convertMetaTypeToJavaTypeValue(Field field, Object dbValue);

    Object convertJavaTypeToMetaTypeValue(Field field, Object javaTypeValue);

    default DataTable JavaBeanMapResult(ResultSet rs) throws SQLException, ClassNotFoundException {
        DataTable list = new DataTable();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            String columnName = (md.getColumnLabel(i)  == null || md.getColumnName(i) =="") ? md.getColumnName(i) : md.getColumnLabel(i);
            Class<?> clazz = MetaTypeCt.getClassForMetaName(md.getColumnClassName(i));
            DataColumn column = new  DataColumn(columnName,clazz.getName());
            list.getColumnMeta().add(column);
        }
        while (rs.next()) {
            DataRow  rowData = new DataRow();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = (md.getColumnLabel(i)  == null || md.getColumnName(i) =="") ? md.getColumnName(i) : md.getColumnLabel(i);
                Class<?> type = MetaTypeCt.getClassForMetaName(md.getColumnClassName(i));
                TypeHandlerBase<?> handler = MetaTypeCt.getTypeHandler(type);
                rowData.put(columnName.toLowerCase(), handler.getResult(rs, columnName));
            }
            list.add(rowData);
        }
        return list;
    }

    default DataTable OriginResult(ResultSet rs) throws SQLException, ClassNotFoundException {
        DataTable list = new DataTable();
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount();

        for (int i = 1; i <= columnCount; i++) {
            String columnName = (md.getColumnLabel(i)  == null || md.getColumnName(i) =="") ? md.getColumnName(i) : md.getColumnLabel(i);
            Class<?> clazz = MetaTypeCt.getClassForMetaName(md.getColumnClassName(i));
            DataColumn column = new  DataColumn(columnName,md.getColumnClassName(i));
            list.getColumnMeta().add(column);
        }
        while (rs.next()) {
             DataRow rowData = new DataRow();
            for (int i = 1; i <= columnCount; i++) {
                String columnName = md.getColumnName(i);
                rowData.put(columnName.toLowerCase(), rs.getObject(i));
            }
            list.add(rowData);
        }
        return list;
    }


    default <T extends BaseModel> List<T> ModelsResult(ResultSet rs, Class<?> targetClass) throws SQLException, InstantiationException, IllegalAccessException {
        List<T> lt = new ArrayList<>();
        ResultSetMetaData md = rs.getMetaData();
        List<String> columns = new ArrayList<>();
        for (int i = 1; i <= md.getColumnCount(); i++) {
            String columnName = (md.getColumnLabel(i)  == null || md.getColumnName(i) =="") ? md.getColumnName(i) : md.getColumnLabel(i);
            columns.add(columnName.toLowerCase());
        }
        List<FieldProperty> fields = CoreUtils.getAllFields(targetClass);
            while (rs.next()) {
                T t = (T)targetClass.newInstance();
                for (FieldProperty field : fields) {

                    String db_filed_name = field.getDbFieldName().toLowerCase();
                    if((md.getColumnCount() != fields.size()) && (!columns.contains(db_filed_name.toLowerCase()))){
                       continue;
                    }
                    try {
                        TypeHandlerBase<?> typeHandlerBase = MetaTypeCt.getTypeHandler(field.getField().getType());
                        Object real_model_filed_value = typeHandlerBase.getResult(rs, db_filed_name);
                        if (real_model_filed_value != null) {
                            field.getField().setAccessible(true);
                            field.getField().set(t, real_model_filed_value);
                        }
                    } catch (SecurityException e) {
                        BitterLogUtil.getInstance().error("bitter-sqlData to java bean object -error,method-listMap2JavaBean:" + e.getMessage(), e);
                        throw new RuntimeException(e);
                    }

                }
                lt.add(t);
            }
        return  lt;
    }

    default  <R extends BaseModel> R SingleModelResult(ResultSet rs, Class<?> targetClass) throws SQLException, InstantiationException, IllegalAccessException {
        List<R> lt = new ArrayList<>();
        lt =  ModelsResult(rs,targetClass);
        if(lt == null || lt.isEmpty())  return  (R)targetClass.newInstance();
        return  lt.get(0);
    }

    default  <R extends BaseModel> R  SingleNullAbleModelResult(ResultSet rs, Class<R> targetClass) throws SQLException, InstantiationException, IllegalAccessException {
        List<R> lt = new ArrayList<>();
        lt =  ModelsResult(rs,targetClass);
        if(lt == null || lt.isEmpty())  return  null;
        return  lt.get(0);
    }

    default List<TypeHandlerBase<?>> getTypeHandlers(ResultSetMetaData metaDataTypes) throws SQLException {
        List<TypeHandlerBase<?>> typeHandlers = new ArrayList<>();
        for (int i = 0, n = metaDataTypes.getColumnCount(); i < n; i++) {
            try {
                Class<?> type = Resources.classForName(metaDataTypes.getColumnClassName(i + 1));
                TypeHandlerBase<?> typeHandler = MetaTypeCt.getTypeHandler(type);
                if (typeHandler == null) {
                    typeHandler = MetaTypeCt.getTypeHandler(Object.class);
                }
                typeHandlers.add(typeHandler);
            } catch (Exception e) {
                typeHandlers.add(MetaTypeCt.getTypeHandler(Object.class));
            }
        }
        return  typeHandlers;
    }


}
