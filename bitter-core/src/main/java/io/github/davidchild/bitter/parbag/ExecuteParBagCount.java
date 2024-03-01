package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.BaseModel;
import lombok.Data;

@Data
public class ExecuteParBagCount<T extends BaseModel> extends ExecuteParBag implements IBagWhere {
    protected WhereContainer whereCondition = new WhereContainer();
    @Override
    public WhereContainer getWhereContainer() {
        return this.getWhereCondition();
    }

    @Override
    public ExecuteParBag getParBag() {
        return  this;
    }
}
