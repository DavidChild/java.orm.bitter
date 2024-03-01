package io.github.davidchild.bitter.op.update;
import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.parbag.ExecuteParBagUpdate;

public class Update<T extends BaseModel> extends DmlQuery implements IUpdateColumnQuery<Update<T>,T>, IWhereQuery<Update<T>,T> {
    public Update(Class<T> clazz) {
        ExecuteParBagUpdate update =  new ExecuteParBagUpdate<>();
        SingleRunner singleRunner = new SingleRunner();
        update.setExecuteEnum(ExecuteEnum.Update);
        update.setModelType(clazz);
        singleRunner.setBagOp(update);
        this.setQuery(singleRunner);
    }

}
