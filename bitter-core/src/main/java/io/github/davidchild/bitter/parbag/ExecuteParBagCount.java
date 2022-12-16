package io.github.davidchild.bitter.parbag;

import java.util.List;

import io.github.davidchild.bitter.parse.BitterPredicate;

public class ExecuteParBagCount<T> extends ExecuteParBag {

    public List<BitterPredicate<T>> condition;

    public List<BitterPredicate<T>> getCondition() {
        return condition;
    }

    public void setCondition(List<BitterPredicate<T>> condition) {
        this.condition = condition;
    }
}
