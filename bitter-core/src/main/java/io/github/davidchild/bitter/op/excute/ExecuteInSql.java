package io.github.davidchild.bitter.op.excute;

import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.SingleRunner;
import io.github.davidchild.bitter.parbag.ExecuteParBagExecute;

import java.util.Arrays;
import java.util.UUID;

public class ExecuteInSql extends DmlQuery {

    public ExecuteInSql(String commandText, Object... params) {
        ExecuteParBagExecute executeBag =  new ExecuteParBagExecute();
        executeBag.setCommandText(commandText);
        if (params != null && params.length > 0) {
            Arrays.stream(params).forEach(
                    (v) -> executeBag.getDynamics().put(UUID.randomUUID().toString(), v));
        }

        SingleRunner singleRunner = new SingleRunner();
        singleRunner.setBagOp(executeBag);
        singleRunner.getBagOp().setExecuteEnum(ExecuteEnum.ExecuteQuery);
        this.setQuery(singleRunner);
    }

}
