package io.github.davidchild.bitter.op.delete;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.IWhereQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagDelete;

public class Delete<T extends BaseModel> extends BaseQuery implements IWhereQuery<Delete<T>,T> {
    public Delete(Class<T> clazz) {
        executeParBag = new ExecuteParBagDelete();
        executeParBag.setExecuteEnum(ExecuteEnum.Delete);
        executeParBag.setType(clazz);
    }


}
