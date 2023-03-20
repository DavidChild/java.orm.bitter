package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.basequery.SubStatement;

import java.util.List;
import java.util.Map;

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

    List<Map<String, Object>> getData();

    IUnionPageAccess toUnionPage();

    IPageAccess where(SubStatement subStatement);

    IPageAccess where(String setWhere, Object... params);

    IPageAccess whereNotBlank(SubStatement subStatement);

    IPageAccess whereNotNull(SubStatement subStatement);

    IPageAccess whereNotNull(String setWhere, Object arg);

    IPageAccess whereNotBlank(String setWhere, String arg);

    IPageAccess where(String setWhere);

    /// <summary>
    /// Or; Note: At this time, Or is always in parallel with the precondition
    /// case：1：（(x.y=="1") or (x.z=3)） or (x.n=4)
    /// case：2：（(x.y=="1") and (x.z=3)） or (x.n=4)
    // It must be noted that there is no such writing method：(x.y=="1") and (x.z=3) or (x.n=4)
    /// When using Or, automatically put all your previous conditions into a () to form conditions with your existing
    /// Or, such as: (previously written conditions) or (existing conditions). This relationship is always the same
    /// </summary>
    /// <param name="setOr">setOr</param>
    IPageAccess or(String setOr);

    /// <summary>
    /// Or; Note: At this time, Or is always in parallel with the precondition
    /// case：1：（(x.y=="1") or (x.z=3)） or (x.n=4)
    /// case：2：（(x.y=="1") and (x.z=3)） or (x.n=4)
    // It must be noted that there is no such writing method：(x.y=="1") and (x.z=3) or (x.n=4)
    /// When using Or, automatically put all your previous conditions into a () to form conditions with your existing
    /// Or, such as: (previously written conditions) or (existing conditions). This relationship is always the same
    /// </summary>
    /// <param name="setOr">setOr</param>
    /// <param name="parmaters...">array of args</param>
    IPageAccess or(String setOr, Object... params);

    IPageAccess setExecuteMode(ExecuteMode executeMode);
}
