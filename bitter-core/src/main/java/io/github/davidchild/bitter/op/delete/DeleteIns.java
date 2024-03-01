package io.github.davidchild.bitter.op.delete;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.SingleRunner;
import io.github.davidchild.bitter.parbag.ExecuteParBagDelete;

public class DeleteIns<T extends BaseModel> extends DmlQuery {


    public DeleteIns(T data) {
        ExecuteParBagDelete deleteBag = new ExecuteParBagDelete();
        SingleRunner singleRunner = new SingleRunner();
        singleRunner.setBagOp(deleteBag);
        singleRunner.getBagOp().setExecuteEnum(ExecuteEnum.Delete);
        singleRunner.getBagOp().setData(data);
        this.setQuery(singleRunner);
    }

    public DeleteIns() {
        ExecuteParBagDelete deleteBag = new ExecuteParBagDelete();
        SingleRunner singleRunner = new SingleRunner();
        singleRunner.setBagOp(deleteBag);
        singleRunner.getBagOp().setExecuteEnum(ExecuteEnum.Delete);
        this.setQuery(singleRunner);

    }

}
