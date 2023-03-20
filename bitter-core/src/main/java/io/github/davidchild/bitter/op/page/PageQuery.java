package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.basequery.BaseQuery;
import io.github.davidchild.bitter.basequery.ExecuteEnum;
import io.github.davidchild.bitter.basequery.ExecuteMode;
import io.github.davidchild.bitter.basequery.SubStatement;
import io.github.davidchild.bitter.bitterlist.BMap;
import io.github.davidchild.bitter.parbag.ExecuteParBagPage;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PageQuery extends BaseQuery implements IPageAccess, Serializable {

    /// <summary>
    /// is executed
    /// </summary>
    private boolean isExAll = false;
    /// <summary>
    /// is skied page
    /// </summary>
    private boolean isExSkip = false;
    /// <summary>
    /// total count
    /// </summary>
    private Integer totalCount = -1;
    /// <summary>
    /// paging data
    /// </summary>
    private BMap pageDt;

    private String lowerCommandText;

    private IUnionPageAccess unionPage = new UnionPage();

    public PageQuery(String pageQuery) {
        this.executeParBag = new ExecuteParBagPage();
        ((ExecuteParBagPage) this.executeParBag).setExecuteMode(ExecuteMode.Cached);
        ((ExecuteParBagPage) this.executeParBag).setCommandText(pageQuery);
    }

    /// <summary>
    /// Get the number of pages
    /// </summary>
    public Integer Count() {
        if (totalCount == -1) {

            return totalCount = getCount();
        } else {
            return totalCount;
        }

    }

    private String getLowerCommandText() {
        if (CoreStringUtils.isEmpty(lowerCommandText)) {
            lowerCommandText = ((ExecuteParBagPage) this.executeParBag).commandText.toLowerCase();
        }
        return lowerCommandText;
    }

    /// <summary>
    /// get columns
    /// </summary>
    private String getColumns() {

        if (getLowerCommandText().indexOf("]") > -1) {
            int indexFrom = getLowerCommandText().indexOf(" from", getLowerCommandText().indexOf("]"));
            return ((ExecuteParBagPage) this.executeParBag).commandText.substring(
                    getLowerCommandText().indexOf("select") + 6, indexFrom - getLowerCommandText().indexOf("select") - 5);
        } else {
            return ((ExecuteParBagPage) this.executeParBag).commandText
                    .substring(getLowerCommandText().indexOf("select") + 6, (getLowerCommandText().indexOf("select") + 6)
                            + getLowerCommandText().indexOf(" from") - getLowerCommandText().indexOf("select") - 5);

        }

    }

    /// <summary>
    /// table
    /// </summary>
    private String getTableName() {
        if (getLowerCommandText().indexOf("]") > -1) {
            int indexFrom = getLowerCommandText().indexOf(" from", getLowerCommandText().indexOf("]"));
            return ((ExecuteParBagPage) this.executeParBag).commandText.substring(indexFrom + 5);
        } else {
            return ((ExecuteParBagPage) this.executeParBag).commandText
                    .substring((((ExecuteParBagPage) this.executeParBag).commandText.toLowerCase().indexOf(" from") + 5));
        }
    }

    /// <summary>
    /// Get all data without paging
    /// </summary>
    /// <returns></returns>
    public IPageAccess getAll() {
        ((ExecuteParBagPage) this.executeParBag).pageIndex = 1;
        ((ExecuteParBagPage) this.executeParBag).pageSize = Integer.MAX_VALUE;
        ((ExecuteParBagPage) this.executeParBag).isPage = true;
        isExAll = true;
        return (IPageAccess) this;
    }

    public IPageAccess orderBy(String order) {
        ((ExecuteParBagPage) this.executeParBag).orderBy.append(String.format("%s ", order));
        return (IPageAccess) this;
    }

    /// <summary
    /// return IPageAccess
    /// </summary>
    /// <param name="pageIndex">Page of number</param>
    public IPageAccess skip(Integer pageIndex) {
        ((ExecuteParBagPage) this.executeParBag).pageIndex = pageIndex;
        ((ExecuteParBagPage) this.executeParBag).isPage = true;
        isExSkip = true;
        return (IPageAccess) this;
    }

    /// <summary>
    /// return IPageAccess
    /// </summary>
    /// <param name="pageSize">how many are displayed per page</param>
    public IPageAccess take(Integer pageSize) {
        ((ExecuteParBagPage) this.executeParBag).pageSize = pageSize;
        ((ExecuteParBagPage) this.executeParBag).isPage = true;
        isExSkip = true;
        return (IPageAccess) this;
    }

    public IPageAccess thenASC(String filedName) {
        ((ExecuteParBagPage) this.executeParBag).orderBy.append(String.format(",%s ASC", filedName));
        return (IPageAccess) this;
    }

    public IPageAccess thenDESC(String filedName) {
        ((ExecuteParBagPage) this.executeParBag).orderBy.append(String.format(",%s DESC", filedName));
        return (IPageAccess) this;
    }

    public MyPage getPage() {
        MyPage mypage = new MyPage();
        mypage = MyPage.getPageObject(((ExecuteParBagPage) this.executeParBag).pageIndex,
                ((ExecuteParBagPage) this.executeParBag).pageSize, getCount());
        return mypage;

    }

    /// <summary>
    /// Return Data Set
    /// </summary>
    /// <returns></returns>
    public BMap getData() {
        try {
            if (this.pageDt == null) {
                setData(true);
                return this.pageDt;
            } else {
                if (isExAll && isExSkip)
                    setData(true);
                return this.pageDt;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    // data:true count false
    private void setData(boolean dataOrCount) throws SQLException {

        ((ExecuteParBagPage) this.executeParBag).pageTableName = this.getTableName();
        ((ExecuteParBagPage) this.executeParBag).pageColumns = this.getColumns();
        if (dataOrCount) {
            ((ExecuteParBagPage) this.executeParBag).setExecuteEnum(ExecuteEnum.PageQuery);
        } else {
            ((ExecuteParBagPage) this.executeParBag).setExecuteEnum(ExecuteEnum.PageCount);
        }
        List<Map<String, Object>> dt;
        dt = super.getData();
        if (!dataOrCount) {
            if (dt != null && dt.size() > 0) {
                this.totalCount = Integer.parseInt(dt.get(0).get("totalcountcheok").toString());
            } else {
                this.totalCount = 0;
            }
        } else {
            if (dt != null && dt.size() > 0) {

                if (dt != null) {
                    BMap bList = new BMap();
                    dt.forEach(item -> {
                        bList.add(item);
                    });
                    this.pageDt = bList;
                }
            } else {
                this.totalCount = 0;
                if (((ExecuteParBagPage) this.executeParBag).isPage) {
                    BMap bList = new BMap();
                    this.pageDt = bList;
                }
            }
            ((ExecuteParBagPage) this.executeParBag).isPage = false;
        }

    }

    /// <summary>
    /// set where condition and the params
    /// </summary>
    /// <param name="setwhere"></param>
    /// <param name="dynamicParms"></param>
    public IPageAccess where(String setWhere, Object... args) {
        if (args != null && args.length > 0) {
            Arrays.stream(args).forEach(item -> {
                ((ExecuteParBagPage) this.executeParBag).dynamics.put(UUID.randomUUID().toString(), item);
            });
        }
        if (CoreStringUtils.isNotEmpty(setWhere)) {
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" and (%s)", setWhere));
        }
        return (IPageAccess) this;
    }

    /// <summary>
    /// set where condition and the params
    /// </summary>
    /// <param name="setwhere"></param>
    /// <param name="dynamicParms"></param>
    public IPageAccess whereNotBlank(String setWhere, String arg) {
        if (CoreStringUtils.isNotNull(arg) && CoreStringUtils.isNotEmpty(arg)) {
            ((ExecuteParBagPage) this.executeParBag).dynamics.put(UUID.randomUUID().toString(), arg);
            if (CoreStringUtils.isNotEmpty(setWhere)) {
                ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" and (%s)", setWhere));
            }
        }

        return (IPageAccess) this;
    }

    /// <summary>
    /// set where condition and the subStatement
    /// </summary>
    /// <param name="setwhere"></param>
    public IPageAccess whereNotNull(SubStatement subStatement) {
        if (CoreStringUtils.isNotEmpty(subStatement.getSubStatement())) {
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" and (%s %s %s)", subStatement.getField(), subStatement.getOp(), subStatement.getSubStatement()));
        }
        return (IPageAccess) this;
    }

    /// <summary>
    /// set where condition and the subStatement
    /// </summary>
    /// <param name="setwhere"></param>
    public IPageAccess whereNotBlank(SubStatement subStatement) {
        if (CoreStringUtils.isNotEmpty(subStatement.getSubStatement())) {
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" and (%s %s %s)", subStatement.getField(), subStatement.getOp(), subStatement.getSubStatement()));
        }
        return (IPageAccess) this;
    }

    /// <summary>
    /// set where condition and the subStatement
    /// </summary>
    /// <param name="setwhere"></param>
    public IPageAccess where(SubStatement subStatement) {
        if (CoreStringUtils.isNotEmpty(subStatement.getSubStatement())) {
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" and (%s %s %s)", subStatement.getField(), subStatement.getOp(), subStatement.getSubStatement()));
        }
        return (IPageAccess) this;
    }

    /// <summary>
    /// set where condition and the params
    /// </summary>
    /// <param name="setwhere"></param>
    /// <param name="dynamicParms"></param>
    public IPageAccess whereNotNull(String setWhere, Object arg) {
        if (CoreStringUtils.isNotNull(arg)) {
            ((ExecuteParBagPage) this.executeParBag).dynamics.put(UUID.randomUUID().toString(), arg);
            if (CoreStringUtils.isNotEmpty(setWhere)) {
                ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" and (%s)", setWhere));
            }
        }

        return (IPageAccess) this;
    }


    /// <summary>
    /// set where condition and the params
    /// </summary>
    /// <param name="setwhere"></param>
    public IPageAccess where(String setWhere) {
        ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" and (%s)", setWhere));
        return (IPageAccess) this;
    }

    /// <summary>
    /// Or; Note: At this time, Or is always in parallel with the precondition
    /// case：1：（(x.y=="1") or (x.z=3)） or (x.n=4)
    /// case：2：（(x.y=="1") and (x.z=3)） or (x.n=4)
    // It must be noted that there is no such writing method：(x.y=="1") and (x.z=3) or (x.n=4)
    /// When using Or, automatically put all your previous conditions into a () to form conditions with your existing
    /// Or, such as: (previously written conditions) or (existing conditions). This relationship is always the same
    /// </summary>
    /// <param name="setOr">setOr</param>
    public IPageAccess or(String setOr) {
        if (CoreStringUtils.isNotEmpty(setOr)) {
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.insert(0, "(");
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(")");
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" Or (%s)", setOr));

        }
        return (IPageAccess) this;
    }

    /// <summary>
    /// Or; Note: At this time, Or is always in parallel with the precondition
    /// case：1：（(x.y=="1") or (x.z=3)） or (x.n=4)
    /// case：2：（(x.y=="1") and (x.z=3)） or (x.n=4)
    // It must be noted that there is no such writing method：(x.y=="1") and (x.z=3) or (x.n=4)
    /// When using Or, automatically put all your previous conditions into a () to form conditions with your existing
    /// Or, such as: (previously written conditions) or (existing conditions). This relationship is always the same
    /// </summary>
    /// <param name="setOr">setOr</param>
    /// <param name="Object...">array of args</param>
    public IPageAccess or(String setOr, Object... args) {

        if (args != null && args.length > 0) {
            Arrays.stream(args).forEach(item -> {
                ((ExecuteParBagPage) this.executeParBag).dynamics.put(UUID.randomUUID().toString(), item);
            });
        }
        if (CoreStringUtils.isNotEmpty(setOr)) {
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.insert(0, "(");
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(")");
            ((ExecuteParBagPage) this.executeParBag).whereBuilder.append(String.format(" Or (%s)", setOr));

        }
        return (IPageAccess) this;
    }

    /// <summary>
    /// get the number of total recode.
    /// </summary>
    /// <returns></returns>
    public Integer getCount() {
        try {
            if (totalCount == -1) {
                setData(false);
                return totalCount;
            } else
                return totalCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /// <summary>
    /// return to UnionPage
    /// </summary>
    /// <param name="page">pageQuery</param>
    /// <returns></returns>
    public IUnionPageAccess toUnionPage() {
        unionPage.union((IPageAccess) this);
        return unionPage;
    }

    /// <summary>
    /// association Table
    /// </summary>
    /// <param name="page">pageQuery</param>
    /// <returns></returns>
    public IUnionPageAccess union(IPageAccess page) {
        unionPage.union((IPageAccess) this);
        unionPage.union(page);
        return (IUnionPageAccess) unionPage;
    }

    public IPageAccess addPreWith(String withSql) {
        if (CoreStringUtils.isEmpty(withSql))
            return (IPageAccess) this;

        ((ExecuteParBagPage) this.executeParBag).preWith = ((ExecuteParBagPage) this.executeParBag).preWith + withSql;
        return (IPageAccess) this;
    }

    public IPageAccess addPreWith(String withSql, Object... args) {
        if (CoreStringUtils.isEmpty(withSql))
            return (IPageAccess) this;
        ((ExecuteParBagPage) this.executeParBag).preWith = ((ExecuteParBagPage) this.executeParBag).preWith + withSql;
        if (args != null && args.length > 0) {
            Arrays.stream(args).forEach(item -> {
                ((ExecuteParBagPage) this.executeParBag).dynamics.put(UUID.randomUUID().toString(), item);
            });
        }
        return (IPageAccess) this;
    }

    /// <summary>
    /// execution mode
    /// </summary>
    /// <param name="excuteMode"></param>
    public IPageAccess setExecuteMode(ExecuteMode executeMode) {
        ((ExecuteParBagPage) this.executeParBag).setExecuteMode(executeMode);
        return (IPageAccess) this;
    }
}
