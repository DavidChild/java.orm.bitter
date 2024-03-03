package io.github.davidchild.bitter.op.insert;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.SingleRunner;
import io.github.davidchild.bitter.op.scope.ThrowingConsumer;
import io.github.davidchild.bitter.parbag.ExecuteParBachInsert;

import java.util.List;

public class BachInsert<T extends BaseModel> extends DmlQuery {

    ExecuteParBachInsert bachInsert = new ExecuteParBachInsert();
    public BachInsert() {
        SingleRunner runner = new SingleRunner();
        bachInsert.setExecuteEnum(ExecuteEnum.BachInsert);
        runner.setBagOp(bachInsert);
        this.setQuery(runner);
    }

    public BachInsert<T> doBachInsert(final ThrowingConsumer<List<Insert<T>>> fn) {
        fn.accept(bachInsert.getList());
        return this;
    }

}
