package io.github.davidchild.bitter.op.scope;

import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.parbag.ExecuteParScope;

import java.util.List;

public class DbScope extends DmlQuery {

    private List<BaseQuery> list;
    ExecuteParScope  scope = new ExecuteParScope();
    public DbScope() {
        SingleRunner scopeRunner = new SingleRunner();
        scope.setExecuteEnum(ExecuteEnum.Scope);
        scopeRunner.setBagOp(scope);
        this.setQuery(scopeRunner);
    }
    public DbScope create(final ThrowingConsumer<List<BaseQuery>> fn) {
        try {
            fn.accept(this.list);
            scope.setScopeList(this.list);
        } catch (Exception ex) {
            ex.printStackTrace();
            scope.setEx(ex);
            scope.setScopeResult(false);
        }
        return this;
    }

}
