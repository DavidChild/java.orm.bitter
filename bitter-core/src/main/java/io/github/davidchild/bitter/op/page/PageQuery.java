package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.parbag.ExecuteParBagPage;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class PageQuery extends DqlQuery implements IWhereQuery<PageQuery,BaseModel>, IColumnQuery<PageQuery,BaseModel>,IOrderQuery<PageQuery,BaseModel> {

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
    public PageQuery() {
        this.setExecuteParBag(new ExecuteParBagPage());
        (this.getExecuteParBag()).setExecuteMode(ExecuteMode.Cached);
    }

    public PageQuery(String pageQuery) {
        this.setExecuteParBag(new ExecuteParBagPage());
        (this.getExecuteParBag()).setExecuteMode(ExecuteMode.Cached);
        ((ExecuteParBagPage) this.getExecuteParBag()).setCommandText(pageQuery);
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
            lowerCommandText = ((ExecuteParBagPage) this.getExecuteParBag()).getCommandText().toLowerCase();
        }
        return lowerCommandText;
    }

    /// <summary>
    /// get columns
    /// </summary>
    private String getColumns() {

        if (getLowerCommandText().indexOf("]") > -1) {
            int indexFrom = getLowerCommandText().indexOf(" from", getLowerCommandText().indexOf("]"));
            return ((ExecuteParBagPage) this.getExecuteParBag()).getCommandText().substring(
                    getLowerCommandText().indexOf("select") + 6, indexFrom - getLowerCommandText().indexOf("select") - 5);
        } else {
            return ((ExecuteParBagPage) this.getExecuteParBag()).getCommandText()
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
            return ((ExecuteParBagPage) this.getExecuteParBag()).getCommandText().substring(indexFrom + 5);
        } else {
            return ((ExecuteParBagPage) this.getExecuteParBag()).getCommandText()
                    .substring((((ExecuteParBagPage) this.getExecuteParBag()).getCommandText().toLowerCase().indexOf(" from") + 5));
        }
    }

    /// <summary>
    /// Get all data without paging
    /// </summary>
    /// <returns></returns>
    public IPageAccess getAll() {
        ((ExecuteParBagPage) this.getExecuteParBag()).setPageIndex(1);
        ((ExecuteParBagPage) this.getExecuteParBag()).setPageSize(Integer.MAX_VALUE);
        ((ExecuteParBagPage) this.getExecuteParBag()).setIsPage(true);
        isExAll = true;
        return (IPageAccess) this;
    }


    /// <summary
    /// return IPageAccess
    /// </summary>
    /// <param name="pageIndex">Page of number</param>
    public PageQuery skip(Integer pageIndex) {
        ((ExecuteParBagPage) this.getExecuteParBag()).setPageIndex(pageIndex);
        ((ExecuteParBagPage) this.getExecuteParBag()).setIsPage(true);
        isExSkip = true;
        return  this;
    }

    /// <summary>
    /// return IPageAccess
    /// </summary>
    /// <param name="pageSize">how many are displayed per page</param>
    public PageQuery take(Integer pageSize) {
        ((ExecuteParBagPage) this.getExecuteParBag()).setPageSize(pageSize);
        ((ExecuteParBagPage) this.getExecuteParBag()).setIsPage(true);
        isExSkip = true;
        return  this;
    }

    public MyPage getPage() {
        MyPage mypage = new MyPage();
        mypage = MyPage.getPageObject(((ExecuteParBagPage) this.getExecuteParBag()).getPageIndex(),
                ((ExecuteParBagPage) this.getExecuteParBag()).getPageSize(), getCount());
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
        ((ExecuteParBagPage) this.getExecuteParBag()).setTableName(this.getTableName());
        ((ExecuteParBagPage) this.getExecuteParBag()).setPageColumns(this.getColumns());
        if (dataOrCount) {
            ((ExecuteParBagPage) this.getExecuteParBag()).setExecuteEnum(ExecuteEnum.PageQuery);
        } else {
            ((ExecuteParBagPage) this.getExecuteParBag()).setExecuteEnum(ExecuteEnum.PageCount);
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
               this.pageDt = dt;
               ((ExecuteParBagPage) this.getExecuteParBag()).setIsPage(false);
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
            }
            return totalCount;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public PageQuery addPreWith(String withSql) {
        if (CoreStringUtils.isEmpty(withSql))
            return  this;
         String pre_sql = ((ExecuteParBagPage) this.getExecuteParBag()).getPreWith() + withSql;
        ((ExecuteParBagPage) this.getExecuteParBag()).setPreWith(pre_sql);
        return  this;
    }

    public PageQuery addPreWith(String withSql, Object... args) {
        if (CoreStringUtils.isEmpty(withSql))
            return  this;
            String pre_sql = ((ExecuteParBagPage) this.getExecuteParBag()).getPreWith() + withSql;
            ((ExecuteParBagPage) this.getExecuteParBag()).setPreWith(pre_sql);
            if (args != null && args.length > 0) {
                Arrays.stream(args).forEach(item -> {
                    (this.getExecuteParBag()).getDynamics().put(UUID.randomUUID().toString(), item);
                });
            }
        return  this;
    }

    /// <summary>
    /// execution mode
    /// </summary>
    /// <param name="excuteMode"></param>
    public PageQuery setExecuteMode(ExecuteMode executeMode) {
        (this.getExecuteParBag()).setExecuteMode(executeMode);
        return this;
    }
}
