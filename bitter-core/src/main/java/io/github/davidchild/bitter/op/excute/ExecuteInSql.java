package io.github.davidchild.bitter.op.excute;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.UUID;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.parbag.ExecuteParBagExecute;

public class ExecuteInSql extends BaseQuery {

    public ExecuteInSql(String commandText, Object... params) {
        executeParBag = new ExecuteParBagExecute();
        executeParBag.setExecuteEnum(ExecuteEnum.ExecuteQuery);
        ((ExecuteParBagExecute)executeParBag).setCommandText(commandText);
        ((ExecuteParBagExecute)executeParBag).setParaMap(new LinkedHashMap<>());
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach(
                (v) -> ((ExecuteParBagExecute)executeParBag).getParaMap().put(UUID.randomUUID().toString(), v));
        }

    }

    public String getCommandText() {
        return ((ExecuteParBagExecute)executeParBag).getCommandText();
    }

    public ExecuteInSql addParams(Object... params) {
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach(
                (v) -> ((ExecuteParBagExecute)executeParBag).getParaMap().put(UUID.randomUUID().toString(), v));
        }
        return this;
    }
}
