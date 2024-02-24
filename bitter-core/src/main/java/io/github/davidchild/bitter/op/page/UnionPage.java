package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.datatable.DataTable;

import java.util.ArrayList;
import java.util.List;

public class UnionPage extends PageQuery {

    public  UnionPage() {
        super();
    }

    private Integer totalCount;
    private List<PageQuery> unionQueryList = new ArrayList<>();

    /// <summary>
    /// Total number of collections
    /// </summary>
    public Integer getCount() {

        int sum = 0;
        for (PageQuery page : unionQueryList) {
            sum += page.getCount();
        }
        return sum;

    }

    /// <summary>
    /// Current page
    /// </summary>
    /// <param name="pageIndex"></param>
    public UnionPage skip(Integer pageIndex) {
        for (PageQuery page : unionQueryList) {
            page.skip(pageIndex);
        }
        return this;
    }

    /// <summary>
    /// How many are displayed per page
    /// </summary>
    /// <param name="pageSize"></param>
    public UnionPage take(Integer pageSize) {
        for (PageQuery page : unionQueryList) {
            page.take(pageSize);
        }
        return this;
    }

    public UnionPage thenASC(String filedName) {
        for (PageQuery page : unionQueryList) {
            page.thenAsc(filedName);
        }
        return this;
    }

    public UnionPage thenDESC(String filedName) {
        for (PageQuery page : unionQueryList) {
            page.thenDesc(filedName);
        }
        return this;
    }

    /// <summary>
    /// Find set data
    /// </summary>
    /// <returns></returns>
    public DataTable getData() {
        DataTable DRS = new DataTable();;
        for (PageQuery page : unionQueryList) {
            DRS.addAll(page.getData());
        }
        return DRS;
    }

    /// <summary>
    /// Statement of the associated collection
    /// </summary>
    /// <param name="page">Incorporated into a collection</param>
    /// <returns></returns>
    public UnionPage union(PageQuery page) {
        unionQueryList.add(page);
        return this;
    }

    /// <summary>
    /// Associated Collection List
    /// </summary>
    /// <param name="listPage">Incorporated into a collection</param>
    /// <returns></returns>
    public UnionPage union(List<PageQuery> listPage) {
        unionQueryList.addAll(listPage);
        return this;
    }

    /// <summary>
    /// Return to SetWhere
    /// </summary>
    /// <param name="setwhere"></param>
    /// <param name="dynamicParms"></param>
    public PageQuery where(String setWhere, Object... dynamicParams) {
        for (PageQuery page : unionQueryList) {
            page.where(setWhere, dynamicParams);
        }
        return this;
    }



    public UnionPage addPreWith(String withSql) {
        for (PageQuery page : unionQueryList) {
            page.addPreWith(withSql);
        }
        return this;
    }

    public UnionPage addPreWith(String withSql, Object... params) {
        for (PageQuery page : unionQueryList) {
            page.addPreWith(withSql, params);
        }
        return this;
    }

    public UnionPage setExecuteMode(ExecuteMode executeMode) {
        for (PageQuery page : unionQueryList) {
            page.setExecuteMode(executeMode);
        }
        return this;
    }
}
