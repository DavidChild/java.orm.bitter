package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.basequery.StatementHandlerResult;
import io.github.davidchild.bitter.basequery.SubStatement;
import io.github.davidchild.bitter.basequery.SubStatementEnum;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.List;
import java.util.UUID;

public class StatementHandler {
    public static StatementHandlerResult handlerStatement(SubStatement subStatement) {
        if(subStatement == null) return  null;
        StatementHandlerResult statementHandlerResult = new StatementHandlerResult();
        if ((subStatement.getArgs() == null || subStatement.getArgs().size() <= 0) && subStatement.getDefaultArg() == null) {
            statementHandlerResult.setStatement("");
            return statementHandlerResult;
        }

        switch (subStatement.getSubStatementEnum()) {
            case in:
                in(subStatement,statementHandlerResult);
                break;
            case allLike:
            case preLike:
            case endLike:
                like(subStatement,statementHandlerResult);
                break;
            case eq:
                eq(subStatement,statementHandlerResult);
                break;
            case lt:
                lt(subStatement,statementHandlerResult);
            case gt:
                gt(subStatement,statementHandlerResult);
                break;
            case leq:
                leq(subStatement,statementHandlerResult);
                break;
            case geq:
                geq(subStatement,statementHandlerResult);
                break;
            case custom:
                Custom(subStatement,statementHandlerResult);
                break;
            default:
                break;
        }
        return createStatementResult(subStatement);
    }
    private static void eq(SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        subStatement.setOp("=");
    }


    private static void lt(SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        subStatement.setOp("<");
    }

    private static void leq(SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        subStatement.setOp("<=");
    }

    private static void gt(SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        subStatement.setOp(">");
    }
    private static void geq(SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        subStatement.setOp(">=");
    }

    private static void Custom(SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        if(subStatement.getArgs() != null && subStatement.getArgs().size()> 0){
            subStatement.getArgs() .forEach(item->{
                String uuid = UUID.randomUUID().toString();
                subStatement.getDynamics().put(uuid,item);
            });
        }
    }
    private static void like(SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        subStatement.setOp("like");

        switch (subStatement.getSubStatementEnum()) {

            case allLike:
                subStatement.setSubStatement("concat('%',?,'%')");
                break;
            case preLike:
                subStatement.setSubStatement("concat('',?,'%')");
                break;
            case endLike:
                subStatement.setSubStatement("concat('%',?,'')");

                break;
            default:
                break;
        }
    }

    private static void in(SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        List<Object> arg = (List<Object>) subStatement.getArgs();
        if ((arg != null && arg.size() <= 1) || ((arg == null || arg.size() == 0) && subStatement.getDefaultArg() != null)) {
            String uuid = UUID.randomUUID().toString();
            subStatement.setOp("=");
            subStatement.setSubStatement("?");
            Object dynamicArg = (arg != null && arg.size() == 1) ? arg.get(0) : subStatement.getDefaultArg();
            subStatement.getDynamics().put(uuid, dynamicArg);
        } else {
            subStatement.setOp("in");
            joinForIn("(", ")", ",", arg,subStatement,statementHandlerResult);
        }
    }
    private static void joinForIn(String prefix, String suffix, String split, List<Object> args,SubStatement subStatement,StatementHandlerResult statementHandlerResult) {
        String subStr = "";
        if (CoreStringUtils.isNotEmpty(prefix)) subStr = prefix;
        for (int i = 0; i < args.size(); i++) {
            String uuid = UUID.randomUUID().toString();
            if (i == args.size() - 1) {
                subStr += "?";
            } else {
                subStr += "?" + split;
            }
            subStatement.getDynamics().put(uuid, args.get(i));
        }

        if (CoreStringUtils.isNotEmpty(suffix)) subStr += suffix;
        subStatement.setSubStatement(subStr);
    }


    private static StatementHandlerResult createStatementResult(SubStatement subStatement){
        // In 和  custom 在各自方法构造的时候已经塞入参数了,这边不必要重复塞入参数容器
        if(subStatement.getSubStatementEnum() != SubStatementEnum.in && subStatement.getSubStatementEnum() != SubStatementEnum.custom){
            String uuid = UUID.randomUUID().toString();
            Object arg =  subStatement.getArgs() == null || subStatement.getArgs().size() == 0 || subStatement.getArgs().get(0) == null ? subStatement.getDefaultArg() : subStatement.getArgs().get(0);
            subStatement.getDynamics().put(uuid,arg);
        }
        String sql = String.format("%s %s %s", CoreStringUtils.isNotEmpty(subStatement.getField())?subStatement.getField() : "", CoreStringUtils.isNotEmpty(subStatement.getOp()) ? subStatement.getOp() : "", subStatement.getStatement());
        subStatement.setStatement(sql);
        StatementHandlerResult statementHandlerResult = new StatementHandlerResult();
        statementHandlerResult.setStatement(sql);
        statementHandlerResult.setSubStatementEnum(subStatement.getSubStatementEnum());
        statementHandlerResult.setDynamics(subStatement.getDynamics());
        return statementHandlerResult;
    }

}
