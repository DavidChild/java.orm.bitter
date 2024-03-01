package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.BaseQuery;

import java.util.List;

public interface  IScopeBag extends IBagOp {
    public List<BaseQuery> getScopeList();
    public Boolean getScopeResult();
}
