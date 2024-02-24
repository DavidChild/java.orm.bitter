package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.datatable.DataTable;

import java.util.List;

/********************************************************************************
 ** auth： davidChild date： 2017/2/20 10:35:38 desc： Ver.: V1.0.0 Copyright (C) 2016 Bitter copyright。
 *********************************************************************************/

public interface IUnionPageAccess {
    Integer getCount();

    /// <summary>
    /// Support for the with as clause
    /// </summary>
    /// <param name="withSql"></param>
    /// <returns></returns>
    IUnionPageAccess addPreWith(String withSql);

    /// <summary>
    /// Support for the with as clause
    /// </summary>
    /// <param name="withSql"></param>
    /// <param name="parmaters"></param>
    /// <returns></returns>
    IUnionPageAccess addPreWith(String withSql, Object... params);

    IUnionPageAccess skip(Integer page);

    IUnionPageAccess take(Integer pageSize);

    IUnionPageAccess thenASC(String filedName);

    IUnionPageAccess thenDESC(String filedName);

    DataTable getData();

    IUnionPageAccess union(IPageAccess page);

    IUnionPageAccess union(List<IPageAccess> unionPage);

    IUnionPageAccess where(String setWhere, Object... params);

    IUnionPageAccess where(String setWhere);


    IUnionPageAccess setExecuteMode(ExecuteMode executeMode);
}
