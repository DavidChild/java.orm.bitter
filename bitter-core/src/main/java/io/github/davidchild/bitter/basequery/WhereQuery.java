package io.github.davidchild.bitter.basequery;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.parse.BitterPredicate;

import java.util.ArrayList;
import java.util.List;

public class WhereQuery<T extends BaseModel> extends BaseQuery {
    protected List<BitterPredicate<T>> condition;

    public List<BitterPredicate<T>> getCondition() {
        return condition;
    }

    public List<SubStatement> statementCondition;
    public List<SubStatement> getStatementCondition() {
        return statementCondition;
    }

    public void Where(BitterPredicate<T> addCondition) {
        if (this.condition == null) {
            this.condition = new ArrayList<>();
        }
        this.condition.add(addCondition);
    }
    public void Where(SubStatement statementCondition) {
        if (this.statementCondition == null) {
            this.statementCondition = new ArrayList<>();
        }
        this.statementCondition.add(statementCondition);
    }



}
