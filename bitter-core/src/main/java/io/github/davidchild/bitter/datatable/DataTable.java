package io.github.davidchild.bitter.datatable;

import java.util.ArrayList;

public class DataTable extends ArrayList<DataRow> {

    private DataRow row;



    private RowColumn dataColumns = new RowColumn();

    public RowColumn getColumnMeta(){
        return  dataColumns;
    }

    public Object tryGetFirstRowFirstColumnValue(String key) {
         if(this == null || this.size() < 1) return null;
         else {
             return this.get(0).getColumnValue(key);
         }
    }
    public <R> R tryGetFirstRowFirstColumnValue( R v) {
        if(this.size() < 1) return (R)v;
        else {
            return this.get(0).getColumnValue(v);
        }
    }
    public <R> R tryGetFirstRowFirstColumnValue(String key, R v) {
        if(this.size() < 1) return  (R)v;
        else {
            return this.get(0).getColumnValue(key,v);
        }
    }
    public <R> R tryGetFirstRowFirstColumnValue(String key, Class<? extends R> clazz) {
        if(this.size() < 1) return null;
        else {
            return this.get(0).getColumnValue(key,clazz);
        }
    }

    public   <OT> BList<OT> toModelList(Class<? extends OT> clazz){
       return DataTableToBList.convetBList(this,clazz,false);
    }
    public   <OT> BList<OT> toModelList(Class<? extends OT> clazz,boolean mapUnderscoreToCamelCase){
        return DataTableToBList.convetBList(this,clazz,mapUnderscoreToCamelCase);
    }

    @Deprecated
    public   <OT> BList<OT> toBList(Class<? extends OT> clazz,boolean mapUnderscoreToCamelCase){
        return DataTableToBList.convetBList(this,clazz,mapUnderscoreToCamelCase);
    }

    @Deprecated
    public   <OT> BList<OT> toBList(Class<? extends OT> clazz){
        return DataTableToBList.convetBList(this,clazz,false);
    }


}
