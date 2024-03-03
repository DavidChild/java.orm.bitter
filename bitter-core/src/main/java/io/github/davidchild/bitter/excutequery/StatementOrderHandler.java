package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.StatementHandlerResult;
import io.github.davidchild.bitter.basequery.SubOrderStatement;
import io.github.davidchild.bitter.tools.CoreStringUtils;

public class StatementOrderHandler {
    public static StatementHandlerResult handlerStatement(SubOrderStatement subOrderStatement) {
        if(subOrderStatement == null) return  null;
        StatementHandlerResult statementHandlerResult = new StatementHandlerResult();

        switch (subOrderStatement.getSubOrderStatementEnum()) {
            case orderDesc:
                orderDesc(subOrderStatement);
                break;
            case orderAsc:
                orderAsc(subOrderStatement);
                break;
            case custom:
                order(subOrderStatement);
                break;
            default:
                break;
        }
        return createStatementResult(subOrderStatement);
    }

    public static void orderDesc(SubOrderStatement subOrderStatement){
        subOrderStatement.setOp("desc");
    }

    public static void orderAsc(SubOrderStatement subOrderStatement){
        subOrderStatement.setOp("asc");
    }

    public  static void order(SubOrderStatement subOrderStatement){
        subOrderStatement.setOp("");
    }

    public  static void custom(SubOrderStatement subOrderStatement){
    }


    public  static StatementHandlerResult createStatementResult(SubOrderStatement subOrderStatement){
        StatementHandlerResult statementHandlerResult = new StatementHandlerResult();
        String sql = "";
        if(CoreStringUtils.isNotEmpty(subOrderStatement.getField()) && CoreStringUtils.isNotEmpty(subOrderStatement.getOp())) {
            //asc // desc
            sql = String.format("%s %s",CoreStringUtils.isNotEmpty(subOrderStatement.getField())?subOrderStatement.getField() : "",subOrderStatement.getOp());
        }else{
            //custom
            sql = String.format("%s",CoreStringUtils.isNotEmpty(subOrderStatement.getStatement())?subOrderStatement.getStatement() : "");
        }

        subOrderStatement.setStatement(sql);
        statementHandlerResult.setStatement(subOrderStatement.getStatement());
        statementHandlerResult.setSubOrderStatementEnum(subOrderStatement.getSubOrderStatementEnum());
        return statementHandlerResult;
    }


}
