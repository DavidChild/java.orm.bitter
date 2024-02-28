package io.github.davidchild.bitter.op.delete;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.parbag.ExecuteParBagDelete;

public class DeleteIns<T extends BaseModel> extends DmlQuery {

    public DeleteIns(T data) {
        setExecuteParBag(new ExecuteParBagDelete());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Delete);
        this.getExecuteParBag().setData(data);
    }

    public DeleteIns() {
        setExecuteParBag(new ExecuteParBagDelete());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Delete);
    }

}
