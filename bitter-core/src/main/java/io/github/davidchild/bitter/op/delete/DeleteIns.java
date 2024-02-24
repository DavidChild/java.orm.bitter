package io.github.davidchild.bitter.op.delete;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.parbag.ExecuteParBagDelete;

public class DeleteIns<T extends BaseModel> extends BaseQuery {

    public DeleteIns(T data) {
        executeParBag = new ExecuteParBagDelete();
        executeParBag.setExecuteEnum(ExecuteEnum.Delete);
        this.executeParBag.setData(data);
    }

    public DeleteIns() {
        executeParBag = new ExecuteParBagDelete();
        executeParBag.setExecuteEnum(ExecuteEnum.Delete);
    }

}
