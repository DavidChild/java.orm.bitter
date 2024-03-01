package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.SubOrderStatement;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ExecuteParBagQuerySelect extends ExecuteParBag implements  IBagWhere,IBagOrder,ITopSizeBag,ICommandBag {

    private String commandText;
    private Integer topSize;
    private WhereContainer whereCondition = new WhereContainer();
    private List<SubOrderStatement> orders = new ArrayList<>();
    @Override
    public ExecuteParBag getParBag() {
        return  this;
    }

    @Override
    public WhereContainer getWhereContainer() {
         return  getWhereCondition();
    }

    @Override
    public List<SubOrderStatement> getOrderContain() {
        return  getOrders();
    }

}
