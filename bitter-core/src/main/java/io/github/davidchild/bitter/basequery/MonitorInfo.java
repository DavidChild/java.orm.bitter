package io.github.davidchild.bitter.basequery;

import java.util.List;

import lombok.Data;

@Data
public class MonitorInfo {

    private String commandSqlText;

    private List<Object> parameters;
    private Integer createSqlTimeMills;
    private Integer executeSqlTimeMills;
    private Integer mappingTimeMills;

}
