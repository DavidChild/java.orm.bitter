package io.github.davidchild.bitter.parbag;

import cn.hutool.core.util.StrUtil;

import java.util.LinkedHashMap;

public class ExecuteParBagPage extends ExecuteParBag {

    /// <summary>
    /// with 子语句
    /// </summary>
    public String preWith;
    /// <summary>
    /// 排序
    /// </summary>
    private StringBuilder orderBy = new StringBuilder("");
    /// <summary>
    /// 分页Index
    /// </summary>
    public Integer pageIndex = 0;
    /// <summary>
    /// 分页Size
    /// </summary>
    public Integer pageSize = 10;
    /// <summary>
    /// 列
    /// </summary>
    public String pageColumns;
    /// <summary>
    /// 是否已进行分页查询
    /// </summary>
    public boolean isPage;
    /// <summary>
    /// 查询语句
    /// </summary>
    public String commandText = "";
    /// <summary>
    /// 查询表
    /// </summary>
    public String pageTableName;
    public LinkedHashMap<String, Object> dynamics = new LinkedHashMap<String, Object>();
    /// <summary>
    /// setWhere
    /// </summary>
    public StringBuilder whereBuilder = new StringBuilder("1=1");

    public String getPreWith() {
        return preWith;
    }

    public void setPreWith(String preWith) {
        this.preWith = preWith;
    }

    public StringBuilder getOrder() {
        return orderBy;
    }

    public void setOrderBy(StringBuilder orderBy) {
        this.orderBy = orderBy;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getPageColumns() {
        return pageColumns;
    }

    public void setPageColumns(String pageColumns) {
        this.pageColumns = pageColumns;
    }

    public boolean isPage() {
        return isPage;
    }

    public void setPage(boolean page) {
        isPage = page;
    }

    public String getCommandText() {
        return commandText;
    }

    public void setCommandText(String commandText) {
        this.commandText = commandText;
    }

    public String getPageTableName() {
        return pageTableName;
    }

    public void setPageTableName(String pageTableName) {
        this.pageTableName = pageTableName;
    }

    public LinkedHashMap<String, Object> getDynamics() {
        return dynamics;
    }

    public void setDynamics(LinkedHashMap<String, Object> dynamics) {
        this.dynamics = dynamics;
    }

    public StringBuilder getWhereBuilder() {
        return whereBuilder;
    }

    public String getOrderBy() {
        if (orderBy == null || orderBy.toString() == null || orderBy.toString() == "") {
            return "";
        }
        String oderby = StrUtil.addSuffixIfNot(orderBy.toString(), ",");
        return StrUtil.unWrap(oderby, ',');
    }

    public void setWhereBuilder(StringBuilder whereBuilder) {
        this.whereBuilder = whereBuilder;
    }
}
