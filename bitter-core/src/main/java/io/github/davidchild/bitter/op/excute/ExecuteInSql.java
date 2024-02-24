package io.github.davidchild.bitter.op.excute;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.parbag.ExecuteParBagExecute;

import java.util.Arrays;
import java.util.UUID;

public class ExecuteInSql extends BaseQuery {
    public ExecuteInSql(String commandText, Object... params) {
        executeParBag = new ExecuteParBagExecute();
        executeParBag.setExecuteEnum(ExecuteEnum.ExecuteQuery);
        ((ExecuteParBagExecute)executeParBag).setCommandText(commandText);
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach(
                (v) -> executeParBag.getDynamics().put(UUID.randomUUID().toString(), v));
        }
    }

}
