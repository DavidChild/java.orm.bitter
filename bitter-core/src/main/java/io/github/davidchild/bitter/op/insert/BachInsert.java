package io.github.davidchild.bitter.op.insert;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.op.scope.ThrowingConsumer;
import io.github.davidchild.bitter.parbag.ExecuteParBachInsert;

import java.util.ArrayList;
import java.util.List;

public class BachInsert<T extends BaseModel> extends DmlQuery {
    private final List<Insert<T>> list;

    public BachInsert() {
        this.setExecuteParBag(new ExecuteParBachInsert<>());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.BachInsert);
        this.list = new ArrayList<>();
    }

    public BachInsert<T> doBachInsert(final ThrowingConsumer<List<Insert<T>>> fn) {
        fn.accept(this.list);
        ((ExecuteParBachInsert) this.getExecuteParBag()).setList(this.list);
        return this;
    }

}
