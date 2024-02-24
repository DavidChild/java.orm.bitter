package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.functional.IfInnerLambda;
import io.github.davidchild.bitter.parbag.IBagWhere;
import io.github.davidchild.bitter.parse.BitterPredicate;
import io.github.davidchild.bitter.tools.CoreStringUtils;

public interface IWhereQuery<OP extends BaseQuery,T extends BaseModel>  {
    public  default OP where(String setWhere, Object... args) {
       IBagWhere iBagWhere = ((IBagWhere)this);
       SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
       subWhereStatement.custom(setWhere,args);
       iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
       return  (OP)this;
    }


    public  default OP where(String setWhere) {
        IBagWhere iBagWhere = ((IBagWhere)this);
        SubWhereStatement subWhereStatement = ((OP)this).createSubWhereStatement();
        subWhereStatement.custom(setWhere,null);
        iBagWhere.getWhereContainer().getSubWhereStatementCondition().add(subWhereStatement);
        return  (OP)this;
    }

    public  default OP where(IfInnerLambda ifInnerLambda,String setWhere, Object... args){
        if(ifInnerLambda.IfOrNot()){
            where(setWhere,args);
        }
        return (OP)this;
    }

    public  default OP whereNotBlank(String setWhere, Object arg) {
        if (CoreStringUtils.isNotNull(arg)) {
            where(setWhere,arg);
        }
        return  (OP)this;
    }
    public  default OP whereNotBlank(String setWhere, String arg, IfInnerLambda ifInnerLambda){
        if(ifInnerLambda.IfOrNot()){
            whereNotBlank(setWhere,arg);
        }
        return (OP)this;
    }
    public  default OP where(String setWhere, IfInnerLambda ifInnerLambda) {
        if(ifInnerLambda.IfOrNot()){
            where(setWhere);
        }
        return (OP)this;
    }

    public  default OP whereNotNull(String setWhere, Object arg) {
        if (CoreStringUtils.isNotNull(arg)) {
        }
        return  (OP)this;
    }
    public  default OP whereNotNull(String setWhere, Object arg,IfInnerLambda ifInnerLambda) {
        if(ifInnerLambda.IfOrNot()){
            whereNotNull(setWhere,arg);
        }
        return (OP)this;
    }

    public default OP where(BitterPredicate<T> predicate) {
        IBagWhere iBagWhere = ((IBagWhere)this);
        iBagWhere.getWhereContainer().getPredicateCondition().add(predicate);
        return (OP)this;
    }

}
