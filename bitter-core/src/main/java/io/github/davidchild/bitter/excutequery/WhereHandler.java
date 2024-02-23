package io.github.davidchild.bitter.excutequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.StatementHandlerResult;
import io.github.davidchild.bitter.basequery.SubStatement;
import io.github.davidchild.bitter.exception.VisitorException;
import io.github.davidchild.bitter.parse.BitterPredicate;
import io.github.davidchild.bitter.parse.BitterVisitor;
import io.github.davidchild.bitter.parse.BitterWrapper;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.util.List;

public class WhereHandler {
    public static  <T extends BaseModel> String getWhere(List<BitterPredicate<T>> lambdas,  List<SubStatement> subStatements,BaseQuery  query) {
        StringBuilder where = new StringBuilder("1=1");
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
                        where.append(" \n ");
                        where.append(String.format("and ( %s )", wp));
                    }
                    if (wrapper.getValue() != null && wrapper.getValue().size() > 0) {
                        wrapper.getValue().forEach(it -> query.getParameters().add(it));
                    }
                } catch (Exception e) {
                    throw new VisitorException("can't support this where expression, please use the expression syntax already supported in bitter,exception is:" + e.getMessage());//todo that can  Navigate to Instance Reference

                } finally {
                    visitor.clear();
                }
            });
        }

        if(subStatements != null && subStatements.size()>0){
            subStatements.forEach(item->{
                StatementHandlerResult statementHandlerResult = StatementHandler.handlerStatement(item);
                if(statementHandlerResult.getStatement() != null && statementHandlerResult.getStatement() != ""){
                    where.append(String.format(" and (%s)", statementHandlerResult.getStatement()));
                    if(statementHandlerResult.getDynamics() != null && statementHandlerResult.getDynamics().size() > 0){
                        statementHandlerResult.getDynamics().forEach((key,param)->{
                            query.getParameters().add(param);
                        });
                    }
                }
            });
        }
        return where.toString();
    }

}
