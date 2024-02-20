package io.github.davidchild.bitter.datatable;

import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.HashMap;

public class DataRow extends HashMap<String, Object> {




    private  <R> R tryCase(R v) {
        Object myValue = v;
        Class<?> clazz = v.getClass();
        if(this.size()>0){
            myValue = this.values().stream().findFirst().orElse(null);;
        }
        myValue = getDefaultValue(myValue,clazz);
        return (R) myValue;
    }


    private <R> R tryCase(String key,R v) {
        Object myValue = v;
        Class<?> clazz = v.getClass();
        if(this.size()>0){
            myValue = this.get(key);
        }
        myValue = getDefaultValue(myValue,clazz);
        return (R) myValue;
    }

    private <R> R tryCase(String key,Class<?> clazz) {
        Object myValue = null;
        if(this.size()>0){
            myValue = this.get(key);
        }
        myValue = getDefaultValue(myValue,clazz);
        return (R) myValue;
    }

    private Object getDefaultValue(Object myv,Class clazz){
        Object myValue = myv;
        if (CoreStringUtils.isNotNull(myv)) {
            if (java.lang.Double.class.equals(clazz)) {
                myValue = java.lang.Double.parseDouble(myv.toString());
            } else if (java.lang.Integer.class.equals(clazz)) {
                myValue = java.lang.Integer.parseInt(myv.toString());
            } else if (java.lang.String.class.equals(clazz)) {
                myValue = myv.toString();
            } else if (java.lang.Float.class.equals(clazz)) {
                myValue = java.lang.Float.parseFloat(myv.toString());
            } else if (java.lang.Short.class.equals(clazz)) {
                myValue = java.lang.Short.parseShort(myv.toString());
            } else if (java.lang.Boolean.class.equals(clazz)) {
                myValue = java.lang.Boolean.getBoolean(myv.toString());
            }
            else if (java.lang.Byte.class.equals(clazz)) {
                myValue = java.lang.Byte.valueOf(myv.toString());
            }else if (java.lang.Enum.class.equals(clazz)) {
                myValue = Enum.valueOf(clazz,myValue.toString());
            }
        }
        return  myValue;
    }

    public Object getColumnValue(String key) {
        return this.get(key);
    }
    public <R> R getColumnValue(String key, R defaultValue) {
        return this.tryCase(defaultValue);
    }
    public <R> R getColumnValue(R defaultValue) {
        return this.tryCase(defaultValue);
    }
    public <R> R getColumnValue(String key, Class<?> clazz) {
        return this.tryCase(key,clazz);
    }

}
