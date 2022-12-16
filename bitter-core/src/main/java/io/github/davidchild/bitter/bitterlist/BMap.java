package io.github.davidchild.bitter.bitterlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson2.JSONObject;

import io.github.davidchild.bitter.tools.CoreStringUtils;
import io.github.davidchild.bitter.tools.JsonUtil;

public class BMap extends ArrayList<Map<String, Object>> {

    private Class<?> myType;

    static String snakeToCamel(String str) {
        StringBuilder builder = new StringBuilder(str);
        for (int i = 0; i < builder.length(); i++) {
            if (builder.charAt(i) == '_') {

                builder.deleteCharAt(i);
                builder.replace(i, i + 1, String.valueOf(Character.toUpperCase(builder.charAt(i))));
            }
        }
        return builder.toString();
    }

    public Class<?> getType() {
        return myType;
    }

    public void setType(Class<?> type) {
        this.myType = type;
    }

    public <R> R tryCase(R v) {
        if (this.size() <= 0)
            return v;
        Object myValue = v;
        Class<?> clazz = v.getClass();
        Map<java.lang.String, Object> map = this.get(0);
        Object myv = map.values().stream().findFirst().orElse(null);
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
        }

        return (R)myValue;
    }

    // if List size<=0 return null
    public Object getFirstRowSomeData(String key) {
        if (this.size() <= 0)
            return null;
        Map<java.lang.String, Object> map = this.get(0);
        return map.get(key);
    }

    public Object getFirstRowData(String key, Object object) {
        if (this.size() <= 0)
            return null;
        Map<java.lang.String, Object> map = this.get(0);
        return map.getOrDefault(key, object);
    }

    public <OT> BList<OT> toBList(Class<? extends OT> clazz, boolean mapUnderscoreToCamelCase) {
        if (this.size() <= 0) {
            return new BList<>(clazz);
        }
        List<Map<String, Object>> finalTempList;
        if (mapUnderscoreToCamelCase) {
            List<Map<String, Object>> finalTempList1 = new ArrayList<>();
            this.forEach(item -> {
                Map<String, Object> temp = new HashMap<>();
                item.forEach((key, value) -> temp.put(snakeToCamel(key), value));
                finalTempList1.add(temp);
            });
            finalTempList = finalTempList1;
        } else {
            finalTempList = this;
        }

        BList<OT> list = new BList<>(clazz);
        finalTempList.forEach(item -> {
            String str = JsonUtil.object2String(item);
            OT ob = JSONObject.parseObject(str, clazz);
            list.add(ob);
        });
        return list;
    }

    public <OT> BList<OT> toBList(Class<? extends OT> clazz) {
        if (this.size() <= 0) {
            return new BList<>(clazz);
        }
        BList<OT> list = new BList<>(clazz);
        this.forEach(item -> {
            String str = JsonUtil.object2String(item);
            OT ob = JsonUtil.string2Object(str, clazz);
            list.add(ob);
        });

        return list;
    }

}
