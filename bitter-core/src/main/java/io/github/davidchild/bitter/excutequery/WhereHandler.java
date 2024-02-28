package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.StatementHandeUtil;
import io.github.davidchild.bitter.basequery.StatementHandlerResult;
import io.github.davidchild.bitter.basequery.SubWhereStatement;
import io.github.davidchild.bitter.exception.VisitorException;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.parse.BitterPredicate;
import io.github.davidchild.bitter.parse.BitterVisitor;
import io.github.davidchild.bitter.parse.BitterWrapper;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.List;

public class WhereHandler {
    public static  <T extends BaseModel> String getWhere(BaseQuery  query) {
        if(query.getExecuteParBag().getInsData() != null) return  WhereHandler.DataWhereCondition(query);

        if(!((IBagWhere)query.getExecuteParBag()).CheckedHaveCondition()) return  "";
        StringBuilder conditionWhere = new StringBuilder(" Where 1=1 ");
        if(!((IBagWhere)query.getExecuteParBag()).CheckedHaveCondition()) return conditionWhere.toString() ;
        List<BitterPredicate<T>> lambdas = ((IBagWhere)query.getExecuteParBag()).getWhereContainer().getPredicateCondition();
        List<SubWhereStatement> subWhereStatements = ((IBagWhere)query.getExecuteParBag()).getWhereContainer().getSubWhereStatementCondition();

        if (lambdas != null && lambdas.size() > 0) {
            lambdas.forEach(lambda -> {
                BitterVisitor visitor = null;
                try {
                    visitor = new BitterVisitor(query.getExecuteParBag().getProperties(),
                            query.getExecuteParBag().getKeyInfo(), "`", "`");
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
                        wrapper.getValue().forEach(v ->  StatementHandeUtil.setParamInBagContainer(query, v));
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
                            StatementHandeUtil.setParamInBagContainer(query, param);
                        });
                    }
                }
            }
        }
        return conditionWhere.toString();
    }

    private static   String DataWhereCondition(BaseQuery query){
        Object keyValue = query.getExecuteParBag().getKeyInfo().getValue();
        String keyName = query.getExecuteParBag().getKeyInfo().getDbName();
        StatementHandeUtil.setParamInBagContainer(query, keyValue);
        return   String.format(" Where %s=?", keyName);

    }

}
