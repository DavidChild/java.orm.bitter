package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.SubColumnStatement;
import io.github.davidchild.bitter.basequery.SubOrderStatement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExecuteParBagSelect<T extends BaseModel> extends ExecuteParBag implements IBagColumn,IBagWhere,IBagOrder,ITopSizeBag {


    protected List<SubOrderStatement> orders = new ArrayList<>();
    protected List<SubColumnStatement> selectColumns = new ArrayList<>();
    protected Integer topSize = 0;

    @Override
    public List<SubColumnStatement> getColumnContain() {
        return this.getSelectColumns();
    }

    protected WhereContainer whereCondition = new WhereContainer();
    @Override
    public WhereContainer getWhereContainer() {
        return this.getWhereCondition();
    }

    @Override
    public List<SubOrderStatement> getOrderContain() {
        return this.getOrders();
    }

    @Override
    public ExecuteParBag getParBag() {
        return this;
    }


}

