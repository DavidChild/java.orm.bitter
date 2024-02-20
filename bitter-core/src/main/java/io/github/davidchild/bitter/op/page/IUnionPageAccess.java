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

    /// <summary>
    /// Or; Note: At this time, Or is always in parallel with the precondition
    /// case：1：（(x.y=="1") or (x.z=3)） or (x.n=4)
    /// case：2：（(x.y=="1") and (x.z=3)） or (x.n=4)
    // It must be noted that there is no such writing method：(x.y=="1") and (x.z=3) or (x.n=4),don't write as such
    /// wrong writing
    /// When using Or, automatically put all your previous conditions into a () to form conditions with your existing
    /// Or, such as: (previously written conditions) or (existing conditions). This relationship is always the same
    /// </summary>
    /// <param name="setOr">setOr</param>
    /// <param name="parmaters">array of args</param>
    IUnionPageAccess or(String setOr, Object... params);

    /// <summary>
    /// Or; Note: At this time, Or is always in parallel with the precondition
    /// case：1：（(x.y=="1") or (x.z=3)） or (x.n=4)
    /// case：2：（(x.y=="1") and (x.z=3)） or (x.n=4)
    // It must be noted that there is no such writing method：(x.y=="1") and (x.z=3) or (x.n=4),don't write as such
    /// wrong writing
    /// When using Or, automatically put all your previous conditions into a () to form conditions with your existing
    /// Or, such as: (previously written conditions) or (existing conditions). This relationship is always the same
    /// </summary>
    /// <param name="setOr">setOr</param>
    IUnionPageAccess or(String setOr);

    IUnionPageAccess setExecuteMode(ExecuteMode executeMode);
}
