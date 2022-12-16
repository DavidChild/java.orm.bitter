package io.github.davidchild.bitter.op.insert;

import java.util.ArrayList;
import java.util.List;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.op.scope.ThrowingConsumer;
import io.github.davidchild.bitter.parbag.ExecuteParBachInsert;

public class BachInsert<T extends BaseModel> extends BaseQuery {
    private final List<Insert<T>> list;

    public BachInsert() {
        this.executeParBag = new ExecuteParBachInsert<>();
        executeParBag.setExecuteEnum(ExecuteEnum.BachInsert);
        this.list = new ArrayList<>();
    }

    public BachInsert<T> doBachInsert(final ThrowingConsumer<List<Insert<T>>> fn) {
        fn.accept(this.list);
        ((ExecuteParBachInsert)this.executeParBag).setList(this.list);
        return this;
    }

}
