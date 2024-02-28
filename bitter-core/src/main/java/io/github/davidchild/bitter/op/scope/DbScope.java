package io.github.davidchild.bitter.op.scope;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.DmlQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.parbag.ExecuteParScope;

import java.util.ArrayList;
import java.util.List;

public class DbScope extends DmlQuery {

    private List<BaseQuery> list;

    public DbScope() {
        this.setExecuteParBag(new ExecuteParScope());
        getExecuteParBag().setExecuteEnum(ExecuteEnum.Scope);
        this.list = new ArrayList<>();
    }

    public DbScope create(final ThrowingConsumer<List<BaseQuery>> fn) {
        try {
            fn.accept(this.list);
            ((ExecuteParScope) this.getExecuteParBag()).setList(this.list);
        } catch (Exception ex) {
            ex.printStackTrace();
            ((ExecuteParScope) this.getExecuteParBag()).setEx(ex);
            ((ExecuteParScope) this.getExecuteParBag()).setScopeResut(false);
        }
        return this;
    }

}
