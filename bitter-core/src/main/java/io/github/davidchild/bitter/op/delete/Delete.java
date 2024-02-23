package io.github.davidchild.bitter.op.delete;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.SubStatement;
import io.github.davidchild.bitter.basequery.WhereQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagDelete;
import io.github.davidchild.bitter.parse.BitterPredicate;

public class Delete<T extends BaseModel> extends WhereQuery<T> {
    public Delete(Class<T> clazz) {
        executeParBag = new ExecuteParBagDelete();
        executeParBag.setExecuteEnum(ExecuteEnum.Delete);
        executeParBag.setType(clazz);
    }

    public Delete<T> where(BitterPredicate<T> condition) {
        super.Where(condition);
        ((ExecuteParBagDelete)executeParBag).condition = this.getCondition();
        return this;
    }

    public Delete<T> where(SubStatement subStatementCondition) {
        super.Where(subStatementCondition);
        ((ExecuteParBagDelete)executeParBag).subStatementCondition = this.getStatementCondition();
        return this;
    }
}
