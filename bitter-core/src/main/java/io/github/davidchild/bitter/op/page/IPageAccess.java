package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.datatable.DataTable;

public interface IPageAccess {

    Integer getCount();

    /// <summary>
    /// Support for the with as clause
    /// </summary>
    /// <param name="withSql"></param>
    /// <returns></returns>
    IPageAccess addPreWith(String withSql);

    /// <summary>
    /// Support for the with as clause
    /// </summary>
    /// <param name="withSql"></param>
    /// <returns></returns>
    IPageAccess addPreWith(String withSql, Object... args);

    IPageAccess getAll();

    IPageAccess orderBy(String oderBy);

    /// <summary>
    /// Current Page
    /// </summary>
    /// <param name="page"></param>
    /// <returns></returns>
    IPageAccess skip(Integer page);

    /// <summary>
    /// Current Pagination
    /// </summary>
    /// <param name="pageSize"></param>
    /// <returns></returns>
    IPageAccess take(Integer pageSize);

    IPageAccess thenASC(String filedName);

    IPageAccess thenDESC(String filedName);

    DataTable getData();


    IPageAccess setExecuteMode(ExecuteMode executeMode);
}
