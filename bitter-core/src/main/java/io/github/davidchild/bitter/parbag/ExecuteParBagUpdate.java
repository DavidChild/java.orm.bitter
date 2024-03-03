package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.SubUpdateColumnStatement;

import java.util.ArrayList;
import java.util.List;

public class ExecuteParBagUpdate<T extends BaseModel> extends ExecuteParBag implements IBagUpdateColumn,IBagWhere,IBagUpdateInsColumn<T> {

    private WhereContainer whereContainer = new WhereContainer();
    private boolean isReSetValueInUpdatePair = false;
    private List<SubUpdateColumnStatement> updateColumnStatements = new ArrayList<>();

    @Override
    public List<SubUpdateColumnStatement> getUpdateColumnContain() {
        return updateColumnStatements;
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
