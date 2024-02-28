package io.github.davidchild.bitter.op.excute;

import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.parbag.ExecuteParBagExecute;

import java.util.Arrays;
import java.util.UUID;

public class ExecuteInSql extends DmlQuery {
    public ExecuteInSql(String commandText, Object... params) {
        setExecuteParBag(new ExecuteParBagExecute());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.ExecuteQuery);
        ((ExecuteParBagExecute) getExecuteParBag()).setCommandText(commandText);
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach(
                (v) -> getExecuteParBag().getDynamics().put(UUID.randomUUID().toString(), v));
        }
    }

}
