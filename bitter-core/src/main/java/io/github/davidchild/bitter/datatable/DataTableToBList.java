package io.github.davidchild.bitter.datatable;

import io.github.davidchild.bitter.tools.JsonUtil;

import java.util.ArrayList;

class DataTableToBList {

    static  <OT> BList<OT> convetBList(DataTable dt, Class<? extends OT> clazz, boolean mapUnderscoreToCamelCase) {
        if (dt == null || dt.size() < 1) {
            return new BList<>(clazz);
        }
        ArrayList<DataRow> finalTempList =  new ArrayList<>();
        if (mapUnderscoreToCamelCase) {
            ArrayList<DataRow> finalTempList1 = new ArrayList<>();
            dt.forEach(item -> {
                DataRow temp = new DataRow();
                item.forEach((key, value) -> temp.put(snakeToCamel(key), value));
                finalTempList1.add(temp);
            });
            finalTempList = finalTempList1;
        }else {
            finalTempList = dt;
        }
        BList<OT> list = new BList<>(clazz);
        finalTempList.forEach(item -> {
            String str = JsonUtil.object2String(item);
            OT ob = JsonUtil.string2Object(str, clazz);
            list.add(ob);
        });
        return list;
    }
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


    <OT> BList<OT> toBList(DataTable dt, Class<? extends OT> clazz) {
        if (dt.size() < 1) {
            return new BList<>(clazz);
        }
        BList<OT> list = new BList<>(clazz);
        dt.forEach(item -> {
            String str = JsonUtil.object2String(item);
            OT ob = JsonUtil.string2Object(str, clazz);
            list.add(ob);
        });
        return list;
    }
}
