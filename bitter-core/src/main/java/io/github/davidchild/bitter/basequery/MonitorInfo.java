package io.github.davidchild.bitter.basequery;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class MonitorInfo {

    private String commandSqlText;
    private LinkedHashMap<String, Object> parameters;
    private Integer createSqlTimeMills;
    private Integer executeSqlTimeMills;
    private Integer mappingTimeMills;

}
