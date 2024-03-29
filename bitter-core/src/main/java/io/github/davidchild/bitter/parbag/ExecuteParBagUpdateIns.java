package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.SubUpdateColumnStatement;

import java.util.ArrayList;
import java.util.List;

public class ExecuteParBagUpdateIns<T extends BaseModel> extends ExecuteParBag implements IBagWhere,IBagUpdateInsColumn<T> {

    private WhereContainer whereContainer = new WhereContainer();
    private List<SubUpdateColumnStatement> updateColumnStatements = new ArrayList<>();
    @Override
    public List<SubUpdateColumnStatement> getUpdateColumnContain() {
        return updateColumnStatements;
    }

    @Override
    public T getInsData() {
        return this.getInsData();
    }

    @Override
    public WhereContainer getWhereContainer() {
        return  whereContainer;
    }

    @Override
    public ExecuteParBag getParBag() {
        return  this;
    }
}
