package io.github.davidchild.bitter.parbag;

public interface   IBagWhere extends  IBagOp {
    public WhereContainer getWhereContainer();
    public default Boolean CheckedHaveCondition(){
        WhereContainer container = this.getWhereContainer();
        if((container.getPredicateCondition() != null && container.getPredicateCondition().size()>0)||
                (container.getSubWhereStatementCondition() != null && container.getSubWhereStatementCondition().size()>0)){
            return  true;
        }else {
            return false;
        }
    }
}
