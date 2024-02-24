package io.github.davidchild.bitter.parbag;

import lombok.Data;

@Data
public class ExecuteParBagDelete<T> extends ExecuteParBag implements IBagWhere {
    private WhereContainer whereCondition = new WhereContainer();
    @Override
    public WhereContainer getWhereContainer() {
        return  this.getWhereCondition();
    }
    @Override
    public ExecuteParBag getParBag() {
        return  this;
    }
}
