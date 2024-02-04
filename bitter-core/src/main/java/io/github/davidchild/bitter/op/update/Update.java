package io.github.davidchild.bitter.op.update;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.WhereQuery;
import io.github.davidchild.bitter.op.FieldFunction;
import io.github.davidchild.bitter.parbag.ExecuteParBagUpdate;
import io.github.davidchild.bitter.parbag.UpdatePair;
import io.github.davidchild.bitter.parse.BitterPredicate;

public class Update<T extends BaseModel> extends WhereQuery<T> {

    public Update(Class<T> clazz) {
        executeParBag = new ExecuteParBagUpdate<>();
        executeParBag.setExecuteEnum(ExecuteEnum.Update);
        executeParBag.setType(clazz);
    }

    public Update<T> where(BitterPredicate<T> condition) {
        super.Where(condition);
        ((ExecuteParBagUpdate) executeParBag).condition = this.getCondition();
        return this;
    }

    public Update<T> setColum(FieldFunction<T> filed, Object object) {
        String filedName = executeParBag.getDbFieldByInnerClassFiled(filed);
        ((ExecuteParBagUpdate) executeParBag).SetUpdatePair(new UpdatePair(filedName, object,filedName));
        return this;
    }

}
