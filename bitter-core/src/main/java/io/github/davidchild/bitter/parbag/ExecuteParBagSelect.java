package io.github.davidchild.bitter.parbag;

import java.util.ArrayList;
import java.util.List;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.parse.BitterPredicate;

public class ExecuteParBagSelect<T extends BaseModel> extends ExecuteParBag {

    public List<BitterPredicate<T>> condition;
    public List<OrderPair> orders;
    public List<String> selectColumns = new ArrayList<>();
    public Integer topSize;

    public List<OrderPair> getOrders() {
        return orders;
    }
}
