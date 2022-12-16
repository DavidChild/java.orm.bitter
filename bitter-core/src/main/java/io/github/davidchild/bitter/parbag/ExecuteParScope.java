package io.github.davidchild.bitter.parbag;

import java.util.List;

import io.github.davidchild.bitter.basequery.BaseQuery;

public class ExecuteParScope extends ExecuteParBag {

    List<BaseQuery> list;
    private boolean success;
    private Exception ex;

    public ExecuteParScope() {
        this.success = true;
    }

    public List<BaseQuery> getList() {
        return list;
    }

    public void setList(List<BaseQuery> list) {
        this.list = list;
    }

    public Exception getEx() {
        return ex;
    }

    public void setEx(Exception ex) {
        this.ex = ex;
    }

    public void setScopeResut(boolean rs) {
        this.success = rs;
    }

    public boolean getScopeResult() {
        return this.success;
    }
}
