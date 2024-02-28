package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.StatementHandlerResult;
import io.github.davidchild.bitter.basequery.SubWhereStatement;
import io.github.davidchild.bitter.basequery.SubWhereStatementEnum;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.List;
import java.util.UUID;

public class StatementWhereHandler {
    public static StatementHandlerResult handlerStatement(SubWhereStatement SubWhereStatement) {
        if(SubWhereStatement == null) return  null;
        StatementHandlerResult statementHandlerResult = new StatementHandlerResult();

        switch (SubWhereStatement.getSubWhereStatementEnum()) {
            case in:
            case notIn:
                in(SubWhereStatement,statementHandlerResult);
                break;
            case allLike:
            case preLike:
            case endLike:
                like(SubWhereStatement,statementHandlerResult);
                break;
            case eq:
                eq(SubWhereStatement,statementHandlerResult);
                break;
            case neq:
                neq(SubWhereStatement,statementHandlerResult);
                break;
            case lt:
                lt(SubWhereStatement,statementHandlerResult);
            case gt:
                gt(SubWhereStatement,statementHandlerResult);
                break;
            case leq:
                leq(SubWhereStatement,statementHandlerResult);
                break;
            case geq:
                geq(SubWhereStatement,statementHandlerResult);
                break;
            case custom:
                custom(SubWhereStatement,statementHandlerResult);
                break;
            default:
                break;
        }
        return createStatementResult(SubWhereStatement,statementHandlerResult);
    }
    private static void eq(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp("=");
        SubWhereStatement.setStatement("?");
    }

    private static void neq(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp("!=");
        SubWhereStatement.setStatement("?");
    }


    private static void lt(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp("<");
        SubWhereStatement.setStatement("?");
    }

    private static void leq(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp("<=");
        SubWhereStatement.setStatement("?");
    }

    private static void gt(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp(">");
        SubWhereStatement.setStatement("?");
    }
    private static void geq(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp(">=");
        SubWhereStatement.setStatement("?");
    }

    private static void custom(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        if(SubWhereStatement.getArgs() != null && SubWhereStatement.getArgs().size()> 0){
            SubWhereStatement.getArgs() .forEach(item->{
                String uuid = UUID.randomUUID().toString();
                statementHandlerResult.getDynamics().put(uuid,item);
            });
        }
    }
    private static void like(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp("like");

        switch (SubWhereStatement.getSubWhereStatementEnum()) {

            case allLike:
                SubWhereStatement.setSubWhereStatement("concat('%',?,'%')");
                break;
            case preLike:
                SubWhereStatement.setSubWhereStatement("concat('',?,'%')");
                break;
            case endLike:
                SubWhereStatement.setSubWhereStatement("concat('%',?,'')");

                break;
            default:
                break;
        }
    }

    private static void in(SubWhereStatement subWhereStatement,StatementHandlerResult statementHandlerResult) {
        List<Object> arg = subWhereStatement.getArgs();
        if ((arg != null && arg.size() <= 1) || ((arg == null || arg.size() == 0) && subWhereStatement.getDefaultArg() != null)) {
            String uuid = UUID.randomUUID().toString();
            if (subWhereStatement.getSubWhereStatementEnum() == SubWhereStatementEnum.notIn) {
                subWhereStatement.setOp("!=");
            } else {
                subWhereStatement.setOp("=");
            }
            subWhereStatement.setSubWhereStatement("?");
            Object dynamicArg = (arg != null && arg.size() == 1) ? arg.get(0) : subWhereStatement.getDefaultArg();
            statementHandlerResult.getDynamics().put(uuid, dynamicArg);
        } else {
            if (subWhereStatement.getSubWhereStatementEnum() == SubWhereStatementEnum.notIn) {
                subWhereStatement.setOp(" not in");
            } else {
                subWhereStatement.setOp("in");
            }
            joinForIn("(", ")", ",", arg, subWhereStatement, statementHandlerResult);
        }
    }
    private static void joinForIn(String prefix, String suffix, String split, List<Object> args,SubWhereStatement subWhereStatement,StatementHandlerResult statementHandlerResult) {
        String subStr = "";
        if (CoreStringUtils.isNotEmpty(prefix)) subStr = prefix;
        for (int i = 0; i < args.size(); i++) {
            String uuid = UUID.randomUUID().toString();
            if (i == args.size() - 1) {
                subStr += "?";
            } else {
                subStr += "?" + split;
            }
            statementHandlerResult.getDynamics().put(uuid, args.get(i));
        }

        if (CoreStringUtils.isNotEmpty(suffix)) subStr += suffix;
        subWhereStatement.setSubWhereStatement(subStr);
    }


    private static StatementHandlerResult createStatementResult(SubWhereStatement subWhereStatement,StatementHandlerResult result){
        // In 和  custom 在各自方法构造的时候已经塞入参数了,这边不必要重复塞入参数容器
        if(subWhereStatement.getSubWhereStatementEnum() != SubWhereStatementEnum.notIn&&subWhereStatement.getSubWhereStatementEnum() != SubWhereStatementEnum.in && subWhereStatement.getSubWhereStatementEnum() != SubWhereStatementEnum.custom){
            String uuid = UUID.randomUUID().toString();
            Object arg =  subWhereStatement.getArgs() == null || subWhereStatement.getArgs().size() == 0 || subWhereStatement.getArgs().get(0) == null ? subWhereStatement.getDefaultArg() : subWhereStatement.getArgs().get(0);
            result.getDynamics().put(uuid,arg);
        }
        String sql = "";
        if(subWhereStatement.getSubWhereStatementEnum() != SubWhereStatementEnum.custom){
           sql = String.format("%s %s %s", CoreStringUtils.isNotEmpty(subWhereStatement.getField())?subWhereStatement.getField() : "", CoreStringUtils.isNotEmpty(subWhereStatement.getOp()) ? subWhereStatement.getOp() : "", subWhereStatement.getStatement());
        }else{
            sql = String.format("%s", subWhereStatement.getStatement());
        }

        result.setStatement(sql);
        result.setSubWhereStatementEnum(subWhereStatement.getSubWhereStatementEnum());
        return result;
    }

}
