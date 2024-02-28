package io.github.davidchild.bitter.op.delete;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.IWhereQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagDelete;

public class Delete<T extends BaseModel> extends DmlQuery implements IWhereQuery<Delete<T>, T> {
    public Delete(Class<T> clazz) {
        setExecuteParBag(new ExecuteParBagDelete());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Delete);
        getExecuteParBag().setType(clazz);
    }


}
