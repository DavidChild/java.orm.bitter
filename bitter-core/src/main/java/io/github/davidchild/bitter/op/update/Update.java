package io.github.davidchild.bitter.op.update;
import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.parbag.ExecuteParBagUpdate;

public class Update<T extends BaseModel> extends BaseQuery {
    public Update(Class<T> clazz) {
        executeParBag = new ExecuteParBagUpdate<>();
        executeParBag.setExecuteEnum(ExecuteEnum.Update);
        executeParBag.setType(clazz);
    }

}
