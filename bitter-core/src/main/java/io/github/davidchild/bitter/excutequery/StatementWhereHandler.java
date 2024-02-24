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
        if ((SubWhereStatement.getArgs() == null || SubWhereStatement.getArgs().size() <= 0) && SubWhereStatement.getDefaultArg() == null) {
            statementHandlerResult.setStatement("");
            return statementHandlerResult;
        }

        switch (SubWhereStatement.getSubWhereStatementEnum()) {
            case in:
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
                Custom(SubWhereStatement,statementHandlerResult);
                break;
            default:
                break;
        }
        return createStatementResult(SubWhereStatement);
    }
    private static void eq(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp("=");
    }


    private static void lt(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp("<");
    }

    private static void leq(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp("<=");
    }

    private static void gt(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp(">");
    }
    private static void geq(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        SubWhereStatement.setOp(">=");
    }

    private static void Custom(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        if(SubWhereStatement.getArgs() != null && SubWhereStatement.getArgs().size()> 0){
            SubWhereStatement.getArgs() .forEach(item->{
                String uuid = UUID.randomUUID().toString();
                SubWhereStatement.getDynamics().put(uuid,item);
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

    private static void in(SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        List<Object> arg = (List<Object>) SubWhereStatement.getArgs();
        if ((arg != null && arg.size() <= 1) || ((arg == null || arg.size() == 0) && SubWhereStatement.getDefaultArg() != null)) {
            String uuid = UUID.randomUUID().toString();
            SubWhereStatement.setOp("=");
            SubWhereStatement.setSubWhereStatement("?");
            Object dynamicArg = (arg != null && arg.size() == 1) ? arg.get(0) : SubWhereStatement.getDefaultArg();
            SubWhereStatement.getDynamics().put(uuid, dynamicArg);
        } else {
            SubWhereStatement.setOp("in");
            joinForIn("(", ")", ",", arg,SubWhereStatement,statementHandlerResult);
        }
    }
    private static void joinForIn(String prefix, String suffix, String split, List<Object> args,SubWhereStatement SubWhereStatement,StatementHandlerResult statementHandlerResult) {
        String subStr = "";
        if (CoreStringUtils.isNotEmpty(prefix)) subStr = prefix;
        for (int i = 0; i < args.size(); i++) {
            String uuid = UUID.randomUUID().toString();
            if (i == args.size() - 1) {
                subStr += "?";
            } else {
                subStr += "?" + split;
            }
            SubWhereStatement.getDynamics().put(uuid, args.get(i));
        }

        if (CoreStringUtils.isNotEmpty(suffix)) subStr += suffix;
        SubWhereStatement.setSubWhereStatement(subStr);
    }


    private static StatementHandlerResult createStatementResult(SubWhereStatement SubWhereStatement){
        // In 和  custom 在各自方法构造的时候已经塞入参数了,这边不必要重复塞入参数容器
        if(SubWhereStatement.getSubWhereStatementEnum() != SubWhereStatementEnum.in && SubWhereStatement.getSubWhereStatementEnum() != SubWhereStatementEnum.custom){
            String uuid = UUID.randomUUID().toString();
            Object arg =  SubWhereStatement.getArgs() == null || SubWhereStatement.getArgs().size() == 0 || SubWhereStatement.getArgs().get(0) == null ? SubWhereStatement.getDefaultArg() : SubWhereStatement.getArgs().get(0);
            SubWhereStatement.getDynamics().put(uuid,arg);
        }
        String sql = String.format("%s %s %s", CoreStringUtils.isNotEmpty(SubWhereStatement.getField())?SubWhereStatement.getField() : "", CoreStringUtils.isNotEmpty(SubWhereStatement.getOp()) ? SubWhereStatement.getOp() : "", SubWhereStatement.getStatement());
        SubWhereStatement.setStatement(sql);
        StatementHandlerResult statementHandlerResult = new StatementHandlerResult();
        statementHandlerResult.setStatement(sql);
        statementHandlerResult.setSubWhereStatementEnum(SubWhereStatement.getSubWhereStatementEnum());
        statementHandlerResult.setDynamics(SubWhereStatement.getDynamics());
        return statementHandlerResult;
    }

}
