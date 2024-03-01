package io.github.davidchild.bitter.op.delete;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.IWhereQuery;
import io.github.davidchild.bitter.basequery.SingleRunner;
import io.github.davidchild.bitter.parbag.ExecuteParBagDelete;

public class Delete<T extends BaseModel> extends DmlQuery implements IWhereQuery<Delete<T>, T> {
    public Delete(Class<T> clazz) {
        SingleRunner singleRunner = new SingleRunner();
        singleRunner.setBagOp(new ExecuteParBagDelete());
        singleRunner.getBagOp().setExecuteEnum(ExecuteEnum.Delete);
        singleRunner.getBagOp().setModelType(clazz);
        this.setQuery(singleRunner);
    }


}
