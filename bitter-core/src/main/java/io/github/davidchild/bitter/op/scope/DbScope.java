package io.github.davidchild.bitter.op.scope;

import java.util.ArrayList;
import java.util.List;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.parbag.ExecuteParScope;

public class DbScope extends BaseQuery {

    private List<BaseQuery> list;

    public DbScope() {
        this.executeParBag = new ExecuteParScope();
        executeParBag.setExecuteEnum(ExecuteEnum.Scope);
        this.list = new ArrayList<>();
    }

    public DbScope create(final ThrowingConsumer<List<BaseQuery>> fn) {
        try {
            fn.accept(this.list);
            ((ExecuteParScope)this.executeParBag).setList(this.list);
        } catch (Exception ex) {
            ex.printStackTrace();
            ((ExecuteParScope)this.executeParBag).setEx(ex);
            ((ExecuteParScope)this.executeParBag).setScopeResut(false);
        }
        return this;
    }

}
