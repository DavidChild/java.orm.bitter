package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.datatable.DataTable;

import java.util.ArrayList;
import java.util.List;

public class UnionPage extends BaseQuery implements IUnionPageAccess {

    private Integer totalCount;
    private List<IPageAccess> unionQueryList = new ArrayList<>();

    /// <summary>
    /// Total number of collections
    /// </summary>
    public Integer getCount() {

        int sum = 0;
        for (IPageAccess page : unionQueryList) {
            sum += page.getCount();
        }
        return sum;

    }

    /// <summary>
    /// Current page
    /// </summary>
    /// <param name="pageIndex"></param>
    public IUnionPageAccess skip(Integer pageIndex) {
        for (IPageAccess page : unionQueryList) {
            page.skip(pageIndex);
        }
        return this;
    }

    /// <summary>
    /// How many are displayed per page
    /// </summary>
    /// <param name="pageSize"></param>
    public IUnionPageAccess take(Integer pageSize) {
        for (IPageAccess page : unionQueryList) {
            page.take(pageSize);
        }
        return this;
    }

    public IUnionPageAccess thenASC(String filedName) {
        for (IPageAccess page : unionQueryList) {
            page.thenASC(filedName);
        }
        return this;
    }

    public IUnionPageAccess thenDESC(String filedName) {
        for (IPageAccess page : unionQueryList) {
            page.thenDESC(filedName);
        }
        return this;
    }

    /// <summary>
    /// Find set data
    /// </summary>
    /// <returns></returns>
    public DataTable getData() {
        DataTable DRS = new DataTable();;
        for (IPageAccess page : unionQueryList) {
            DRS.addAll(page.getData());
        }
        return DRS;
    }

    /// <summary>
    /// Statement of the associated collection
    /// </summary>
    /// <param name="page">Incorporated into a collection</param>
    /// <returns></returns>
    public IUnionPageAccess union(IPageAccess page) {
        unionQueryList.add(page);
        return this;
    }

    /// <summary>
    /// Associated Collection List
    /// </summary>
    /// <param name="listPage">Incorporated into a collection</param>
    /// <returns></returns>
    public IUnionPageAccess union(List<IPageAccess> listPage) {
        unionQueryList.addAll(listPage);
        return this;
    }

    /// <summary>
    /// Return to SetWhere
    /// </summary>
    /// <param name="setwhere"></param>
    /// <param name="dynamicParms"></param>
    public IUnionPageAccess where(String setWhere, Object... dynamicParams) {
        for (IPageAccess page : unionQueryList) {
            page.where(setWhere, dynamicParams);
        }
        return this;
    }

    /// <summary>
    /// setWhere
    /// </summary>
    /// <param name="setwhere"></param>
    public IUnionPageAccess where(String setWhere) {
        for (IPageAccess page : unionQueryList) {
            page.where(setWhere);
        }
        return this;
    }

    /// <summary>
    /// Or; Note: At this time, Or is always in parallel with the precondition
    /// For example: 1: ((x.y=="1") or (x.z=3)) or (x.n=4)
    /// For example: 2: ((x.y=="1") and (x.z=3)) or (x.n=4)
    /// It must be noted that there is no such writing method: (x.y=="1") and (x.z=3) or (x.n=4), so as to eliminate
    /// this wrong writing method
    /// </summary>
    /// <param name="setOr"></param>
    public IUnionPageAccess or(String setOr) {
        for (IPageAccess page : unionQueryList) {
            page.or(setOr);
        }
        return this;
    }

    /// <summary>
    /// Or; Note: At this time, Or is always in parallel with the precondition
    /// For example: 1: ((x.y=="1") or (x.z=3)) or (x.n=4)
    /// For example: 2: ((x.y=="1") and (x.z=3)) or (x.n=4)
    /// It must be noted that there is no such writing method: (x.y=="1") and (x.z=3) or (x.n=4), so as to eliminate
    /// this wrong writing method
    /// </summary>
    /// <param name="setOr"></param>
    public IUnionPageAccess or(String setOr, Object... dynamicParams) {
        for (IPageAccess page : unionQueryList) {
            page.or(setOr, dynamicParams);
        }
        return this;
    }

    public IUnionPageAccess addPreWith(String withSql) {
        for (IPageAccess page : unionQueryList) {
            page.addPreWith(withSql);
        }
        return this;
    }

    public IUnionPageAccess addPreWith(String withSql, Object... params) {
        for (IPageAccess page : unionQueryList) {
            page.addPreWith(withSql, params);
        }
        return this;
    }

    public IUnionPageAccess setExecuteMode(ExecuteMode executeMode) {
        for (IPageAccess page : unionQueryList) {
            page.setExecuteMode(executeMode);
        }
        return this;
    }
}
