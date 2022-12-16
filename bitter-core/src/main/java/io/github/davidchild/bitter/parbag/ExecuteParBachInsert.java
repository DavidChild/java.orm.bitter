package io.github.davidchild.bitter.parbag;

import java.util.List;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.op.insert.Insert;

public class ExecuteParBachInsert<T extends BaseModel> extends ExecuteParBag {
    List<Insert<T>> list;

    public List<Insert<T>> getList() {
        return list;
    }

    public void setList(List<Insert<T>> list) {
        this.list = list;
    }
}
