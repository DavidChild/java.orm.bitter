package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.SubColumnStatement;
import io.github.davidchild.bitter.basequery.SubOrderStatement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExecuteParBagSelect<T extends BaseModel> extends ExecuteParBag implements IBagColumn,IBagWhere,IBagOrder {

    private WhereContainer whereCondition = new WhereContainer();
    private List<SubOrderStatement> orders = new ArrayList<>();
    private List<SubColumnStatement> selectColumns = new ArrayList<>();
    private Integer topSize;
    @Override
    public List<SubColumnStatement> getColumnContain() {
        return  this.getSelectColumns();
    }

    @Override
    public WhereContainer getWhereContainer() {
        return  this.getWhereCondition();
    }

    @Override
    public List<SubOrderStatement> getOrderContain() {
        return  this.getOrders();
    }

    @Override
    public ExecuteParBag getParBag() {
        return   this;
    }


}