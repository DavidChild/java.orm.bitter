package io.github.davidchild.bitter.op.update;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.IUpdateColumnQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagUpdate;

public class UpdateIns<T extends BaseModel>  extends DmlQuery implements IUpdateColumnQuery<UpdateIns<T>,T> {

    public UpdateIns(T data) {
        setExecuteParBag(new ExecuteParBagUpdate<>());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Update);
        this.getExecuteParBag().setData(data);
    }

    public UpdateIns() {
        setExecuteParBag(new ExecuteParBagUpdate<>());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Update);
    }

}
