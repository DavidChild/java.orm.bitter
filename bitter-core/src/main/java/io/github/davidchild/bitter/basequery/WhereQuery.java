package io.github.davidchild.bitter.basequery;

import java.util.ArrayList;
import java.util.List;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.parse.BitterPredicate;

public class WhereQuery<T extends BaseModel> extends BaseQuery {
    protected List<BitterPredicate<T>> condition;

    public List<BitterPredicate<T>> getCondition() {
        return condition;
    }

    public void Where(BitterPredicate<T> addCondition) {
        if (this.condition == null) {
            this.condition = new ArrayList<>();
        }
        this.condition.add(addCondition);
    }
}
