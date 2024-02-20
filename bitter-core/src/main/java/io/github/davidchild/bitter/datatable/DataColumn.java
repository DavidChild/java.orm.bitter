package io.github.davidchild.bitter.datatable;

import lombok.Data;

@Data
public class DataColumn {
    public DataColumn(String columnName, String clazz){
        this.columnType = clazz;
        this.columnName = columnName;
    }
    public String columnName;
    public String columnType;
}
