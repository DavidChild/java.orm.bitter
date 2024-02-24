package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.StatementHandlerResult;
import io.github.davidchild.bitter.basequery.SubOrderStatement;
import io.github.davidchild.bitter.parbag.IBagOrder;

import java.util.List;

public class OrderHandler {
    public static  <T extends BaseModel> String getOrder(BaseQuery  query) {
        StringBuilder order = new StringBuilder("ORDER BY ");
        if(!((IBagOrder)query.getExecuteParBag()).CheckedHaveOrder()) return  "" ;
        List<SubOrderStatement> subOrderStatements = ((IBagOrder)query.getExecuteParBag()).getOrderContain();
        if(subOrderStatements != null && subOrderStatements.size()>0){
            for(int i=0;i<subOrderStatements.size(); i++){
                StatementHandlerResult statementHandlerResult = StatementOrderHandler.handlerStatement(subOrderStatements.get(i));
                if(statementHandlerResult.getStatement() != null && statementHandlerResult.getStatement() != ""){
                    order.append(String.format(" %s", (i!=subOrderStatements.size())? (statementHandlerResult.getStatement()+","):statementHandlerResult.getStatement()));
                }
            }
        }
        return order.toString();
    }

}
