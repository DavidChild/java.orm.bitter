package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.SubStatement;
import io.github.davidchild.bitter.parse.BitterPredicate;

import java.util.List;

public class ExecuteParBagDelete<T> extends ExecuteParBag {

    public List<BitterPredicate<T>> condition;
    public List<SubStatement> subStatementCondition;

    public List<BitterPredicate<T>> getCondition() {
        return condition;
    }

    public List<SubStatement>  getSubStatementCondition() {
        return subStatementCondition;
    }

    public void setCondition(List<BitterPredicate<T>> condition) {
        this.condition = condition;
    }
}
