package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.parbag.ExecuteParBagPage;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class PageQuery extends BaseQuery implements IWhereQuery<PageQuery,BaseModel>, IColumnQuery<PageQuery,BaseModel>,IOrderQuery<PageQuery,BaseModel> {

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
    private DataTable pageDt;

    private String lowerCommandText;

    private UnionPage unionPage = new UnionPage();
    public PageQuery() {
        this.executeParBag = new ExecuteParBagPage();
        ((ExecuteParBagPage) this.executeParBag).setExecuteMode(ExecuteMode.Cached);
    }

    public PageQuery(String pageQuery) {
        this.executeParBag = new ExecuteParBagPage();
        ((ExecuteParBagPage) this.executeParBag).setExecuteMode(ExecuteMode.Cached);
        ((ExecuteParBagPage) this.executeParBag).setCommandText(pageQuery);
    }

    /// <summary>
    /// Get the number of pages
    /// </summary>
    private Integer Count() {
        if (totalCount == -1) {
            return totalCount = getCount();
        } else {
            return totalCount;
        }

    }

    private String getLowerCommandText() {
        if (CoreStringUtils.isEmpty(lowerCommandText)) {
            lowerCommandText = ((ExecuteParBagPage) this.executeParBag).getCommandText().toLowerCase();
        }
        return lowerCommandText;
    }

    /// <summary>
    /// get columns
    /// </summary>
    private String getColumns() {

        if (getLowerCommandText().indexOf("]") > -1) {
            int indexFrom = getLowerCommandText().indexOf(" from", getLowerCommandText().indexOf("]"));
            return ((ExecuteParBagPage) this.executeParBag).getCommandText().substring(
                    getLowerCommandText().indexOf("select") + 6, indexFrom - getLowerCommandText().indexOf("select") - 5);
        } else {
            return ((ExecuteParBagPage) this.executeParBag).getCommandText()
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
            return ((ExecuteParBagPage) this.executeParBag).getCommandText().substring(indexFrom + 5);
        } else {
            return ((ExecuteParBagPage) this.executeParBag).getCommandText()
                    .substring((((ExecuteParBagPage) this.executeParBag).getCommandText().toLowerCase().indexOf(" from") + 5));
        }
    }

    /// <summary>
    /// Get all data without paging
    /// </summary>
    /// <returns></returns>
    public IPageAccess getAll() {
        ((ExecuteParBagPage) this.executeParBag).setPageIndex(1);
        ((ExecuteParBagPage) this.executeParBag).setPageSize(Integer.MAX_VALUE);
        ((ExecuteParBagPage) this.executeParBag).setIsPage(true);
        isExAll = true;
        return (IPageAccess) this;
    }


    /// <summary
    /// return IPageAccess
    /// </summary>
    /// <param name="pageIndex">Page of number</param>
    public PageQuery skip(Integer pageIndex) {
        ((ExecuteParBagPage) this.executeParBag).setPageIndex(pageIndex);
        ((ExecuteParBagPage) this.executeParBag).setIsPage(true);
        isExSkip = true;
        return  this;
    }

    /// <summary>
    /// return IPageAccess
    /// </summary>
    /// <param name="pageSize">how many are displayed per page</param>
    public PageQuery take(Integer pageSize) {
        ((ExecuteParBagPage) this.executeParBag).setPageSize(pageSize);
        ((ExecuteParBagPage) this.executeParBag).setIsPage(true);
        isExSkip = true;
        return  this;
    }

    

    public MyPage getPage() {
        MyPage mypage = new MyPage();
        mypage = MyPage.getPageObject(((ExecuteParBagPage) this.executeParBag).getPageIndex(),
                ((ExecuteParBagPage) this.executeParBag).getPageSize(), getCount());
        return mypage;

    }

    /// <summary>
    /// Return Data Set
    /// </summary>
    /// <returns></returns>
    public DataTable getData() {
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
        ((ExecuteParBagPage) this.executeParBag).setTableName(this.getTableName());
        ((ExecuteParBagPage) this.executeParBag).setPageColumns(this.getColumns());
        if (dataOrCount) {
            ((ExecuteParBagPage) this.executeParBag).setExecuteEnum(ExecuteEnum.PageQuery);
        } else {
            ((ExecuteParBagPage) this.executeParBag).setExecuteEnum(ExecuteEnum.PageCount);
        }
        DataTable dt;
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
                    DataTable bList = new DataTable();
                    dt.forEach(item -> {
                        bList.add(item);
                    });
                    this.pageDt = bList;
                }
            } else {
                this.totalCount = 0;
                if (((ExecuteParBagPage) this.executeParBag).getIsPage()) {
                    DataTable bList = new DataTable();
                    this.pageDt = bList;
                }
            }
            ((ExecuteParBagPage) this.executeParBag).setIsPage(false);
        }

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
    public UnionPage toUnionPage() {
        unionPage.union((PageQuery) this);
        return unionPage;
    }

    /// <summary>
    /// association Table
    /// </summary>
    /// <param name="page">pageQuery</param>
    /// <returns></returns>
    public UnionPage union(PageQuery page) {
        unionPage.union((PageQuery) this);
        unionPage.union(page);
        return  unionPage;
    }

    public PageQuery addPreWith(String withSql) {
        if (CoreStringUtils.isEmpty(withSql))
            return (PageQuery) this;
         String pre_sql = ((ExecuteParBagPage) this.executeParBag).getPreWith() + withSql;
        ((ExecuteParBagPage) this.executeParBag).setPreWith(pre_sql);
        return (PageQuery) this;
    }

    public PageQuery addPreWith(String withSql, Object... args) {
        if (CoreStringUtils.isEmpty(withSql))
            return (PageQuery) this;
            String pre_sql = ((ExecuteParBagPage) this.executeParBag).getPreWith() + withSql;
            ((ExecuteParBagPage) this.executeParBag).setPreWith(pre_sql);
            if (args != null && args.length > 0) {
                Arrays.stream(args).forEach(item -> {
                    ((ExecuteParBagPage) this.executeParBag).getDynamics().put(UUID.randomUUID().toString(), item);
                });
            }
        return (PageQuery) this;
    }

    /// <summary>
    /// execution mode
    /// </summary>
    /// <param name="excuteMode"></param>
    public PageQuery setExecuteMode(ExecuteMode executeMode) {
        ((ExecuteParBagPage) this.executeParBag).setExecuteMode(executeMode);
        return (PageQuery) this;
    }
}
