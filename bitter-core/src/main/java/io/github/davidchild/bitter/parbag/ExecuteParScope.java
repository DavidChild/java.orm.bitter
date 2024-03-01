package io.github.davidchild.bitter.parbag;

import io.github.davidchild.bitter.basequery.BaseQuery;

import java.util.List;

public class ExecuteParScope extends ExecuteParBag implements  IScopeBag {

    List<BaseQuery> list;
    private Boolean success;
    private Exception ex;

    public ExecuteParScope() {
        this.success = true;
    }

    public void setScopeList(List<BaseQuery> list) {
        this.list = list;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public void setScopeResult(boolean rs) {
        this.success = rs;
    }

    @Override
    public List<BaseQuery> getScopeList() {
        return  list;
    }

    public Boolean getScopeResult() {
        return this.success;
    }

    @Override
    public ExecuteParBag getParBag() {
        return  this;
    }
}
