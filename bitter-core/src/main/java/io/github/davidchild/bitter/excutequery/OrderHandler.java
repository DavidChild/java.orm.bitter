package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.StatementHandlerResult;
import io.github.davidchild.bitter.basequery.SubOrderStatement;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.parbag.IBagOrder;

import java.util.List;

public class OrderHandler {
    public static  <T extends BaseModel> RunnerParam getOrder(IBagOrder bagOrder) {
        RunnerParam runnerParam = new RunnerParam();
        StringBuilder order = new StringBuilder(" ORDER BY");
        if(!(bagOrder.CheckedHaveOrder())) {
            runnerParam.setCommand("");
            return  runnerParam;
        };
        List<SubOrderStatement> subOrderStatements = bagOrder.getOrderContain();
        if(subOrderStatements != null && subOrderStatements.size()>0){
            for(int i=0;i<subOrderStatements.size(); i++){
                StatementHandlerResult statementHandlerResult = StatementOrderHandler.handlerStatement(subOrderStatements.get(i));
                if(statementHandlerResult.getStatement() != null && statementHandlerResult.getStatement() != ""){
                    order.append(String.format(" %s", ((i+1)!=subOrderStatements.size())? (statementHandlerResult.getStatement()+","):statementHandlerResult.getStatement()));
                }
            }
        }
         runnerParam.setCommand(order.toString());
        return  runnerParam;
    }

}
