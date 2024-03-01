package io.github.davidchild.bitter.op.update;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.IUpdateColumnQuery;
import io.github.davidchild.bitter.basequery.SingleRunner;
import io.github.davidchild.bitter.parbag.ExecuteParBagUpdate;

public class UpdateIns<T extends BaseModel>  extends DmlQuery implements IUpdateColumnQuery<UpdateIns<T>,T> {

    ExecuteParBagUpdate update = new ExecuteParBagUpdate();
    public UpdateIns(T data) {
        SingleRunner singleRunner = new SingleRunner();
        update.setExecuteEnum(ExecuteEnum.Update);
        update.setData(data);
         singleRunner.setBagOp(update);
        this.setQuery(singleRunner);
    }

    public UpdateIns() {
        SingleRunner singleRunner = new SingleRunner();
        this.setQuery(singleRunner);
        update.setExecuteEnum(ExecuteEnum.Update);
        singleRunner.setBagOp(update);
        this.setQuery(singleRunner);
    }

}
