package io.github.davidchild.bitter.basequery;

import lombok.Data;

import java.util.LinkedHashMap;

@Data
public class StatementHandlerResult {
    private String statement;
    private LinkedHashMap<String, Object> dynamics;
    private SubStatementEnum subStatementEnum;
}
