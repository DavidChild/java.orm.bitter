package io.github.davidchild.bitter.op.update;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.WhereQuery;
import io.github.davidchild.bitter.parbag.ExecuteParBagUpdate;

public class UpdateIns<T extends BaseModel> extends WhereQuery<T> {

    public UpdateIns(T data) {
        executeParBag = new ExecuteParBagUpdate<>();
        executeParBag.setExecuteEnum(ExecuteEnum.Update);
        this.executeParBag.setData(data);
    }

    public UpdateIns() {
        executeParBag = new ExecuteParBagUpdate<>();
        executeParBag.setExecuteEnum(ExecuteEnum.Update);
    }

}
