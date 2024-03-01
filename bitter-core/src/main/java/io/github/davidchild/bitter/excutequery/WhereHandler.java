package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.StatementHandeUtil;
import io.github.davidchild.bitter.basequery.StatementHandlerResult;
import io.github.davidchild.bitter.basequery.SubWhereStatement;
import io.github.davidchild.bitter.connection.RunnerParam;
import io.github.davidchild.bitter.dbtype.DataValue;
import io.github.davidchild.bitter.exception.VisitorException;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.parse.BitterPredicate;
import io.github.davidchild.bitter.parse.BitterVisitor;
import io.github.davidchild.bitter.parse.BitterWrapper;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.List;

public class WhereHandler {
    public static  <T extends BaseModel> RunnerParam getWhere(T data, IBagWhere  iBagWhere, List<DataValue> fieldProperties, DataValue keyInfo) {
        if(data != null) return  WhereHandler.DataWhereCondition(keyInfo);
        RunnerParam runnerParam = new RunnerParam();

        if(!iBagWhere.CheckedHaveCondition()) {
            runnerParam.setCommand("");
            return  runnerParam;
        }

        StringBuilder conditionWhere = new StringBuilder(" Where 1=1");
        List<BitterPredicate<T>> lambdas = iBagWhere.getWhereContainer().getPredicateCondition();
        List<SubWhereStatement> subWhereStatements =iBagWhere.getWhereContainer().getSubWhereStatementCondition();
        if (lambdas != null && lambdas.size() > 0) {
            lambdas.forEach(lambda -> {
                BitterVisitor visitor = null;
                try {
                    visitor = new BitterVisitor(fieldProperties,
                             keyInfo, "`", "`");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                try {

                    BitterWrapper wrapper = lambda.sql(visitor);
                    String wp = wrapper.getKey().toString();
                    if (wp != null && CoreStringUtils.isNotEmpty(wp)) {
                        conditionWhere.append(String.format(" and ( %s )", wp));
                    }
                    if (wrapper.getValue() != null && wrapper.getValue().size() > 0) {
                        wrapper.getValue().forEach(v ->  StatementHandeUtil.setRunnerParamContainer(runnerParam, v));
                    }
                } catch (Exception e) {
                    throw new VisitorException("can't support this where expression, please use the expression syntax already supported in bitter,exception is:" + e.getMessage());//todo that can  Navigate to Instance Reference

                } finally {
                    visitor.clear();
                }
            });
        }

        if(subWhereStatements != null && subWhereStatements.size()>0){
            for(SubWhereStatement item : subWhereStatements) {
                StatementHandlerResult statementHandlerResult = StatementWhereHandler.handlerStatement(item);
                if(statementHandlerResult.getStatement() != null && statementHandlerResult.getStatement() != ""){
                    conditionWhere.append(String.format(" and (%s)", statementHandlerResult.getStatement()));
                    if(statementHandlerResult.getDynamics() != null && statementHandlerResult.getDynamics().size() > 0){
                        statementHandlerResult.getDynamics().forEach((key,param)->{
                            StatementHandeUtil.setRunnerParamContainer(runnerParam, param);
                        });
                    }
                }
            }
        }
        runnerParam.setCommand(conditionWhere.toString());
        return runnerParam;
    }

    private static   RunnerParam DataWhereCondition(DataValue keyInfo){
        RunnerParam runnerParam = new RunnerParam();
        Object keyValue = keyInfo.getValue();
        String keyName = keyInfo.getDbName();
        StatementHandeUtil.setRunnerParamContainer(runnerParam, keyValue);
        runnerParam.setCommand(String.format(" Where %s=?", keyName));
        return  runnerParam;

    }

}
