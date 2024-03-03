package io.github.davidchild.bitter.op.page;

import io.github.davidchild.bitter.BaseModel;
import io.github.davidchild.bitter.basequery.*;
import io.github.davidchild.bitter.datatable.DataTable;
import io.github.davidchild.bitter.parbag.ExecuteParBagPage;
import io.github.davidchild.bitter.tools.CoreStringUtils;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.UUID;

public class PageQuery extends DqlQuery implements IWhereQuery<PageQuery,BaseModel>, IOrderQuery<PageQuery,BaseModel> {

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


     private ExecuteParBagPage pageBag = new ExecuteParBagPage();

    SingleRunner runner = new SingleRunner();
    public PageQuery() {
        pageBag.setExecuteMode(ExecuteMode.Cached);
        pageBag.setExecuteEnum(ExecuteEnum.PageQuery);
        runner.setBagOp(pageBag);
        this.setQuery(runner);
    }

    public PageQuery(String pageQuery) {
        pageBag.setExecuteMode(ExecuteMode.Cached);
        pageBag.setExecuteEnum(ExecuteEnum.PageQuery);
        pageBag.setCommandText(pageQuery);
        runner.setBagOp(pageBag);
        this.setQuery(runner);
    }

    /// <summary>
    /// Get the number of pages
    /// </summary>
    private Integer count() {
        if (totalCount == -1) {
            return totalCount = getCount();
        } else {
            return totalCount;
        }

    }


    /// <summary>
    /// Get all data without paging
    /// </summary>
    /// <returns></returns>
    public IPageAccess getAll() {
        pageBag.setPageIndex(1);
        pageBag.setPageSize(Integer.MAX_VALUE);
        pageBag.setIsPage(true);
        isExAll = true;
        return (IPageAccess) this;
    }


    /// <summary
    /// return IPageAccess
    /// </summary>
    /// <param name="pageIndex">Page of number</param>
    public PageQuery skip(Integer pageIndex) {
        pageBag.setPageIndex(pageIndex);
        pageBag.setIsPage(true);
        isExSkip = true;
        return  this;
    }

    /// <summary>
    /// return IPageAccess
    /// </summary>
    /// <param name="pageSize">how many are displayed per page</param>
    public PageQuery take(Integer pageSize) {
        pageBag.setPageSize(pageSize);
        pageBag.setIsPage(true);
        isExSkip = true;
        return  this;
    }

    public MyPage getPage() {
        MyPage mypage = new MyPage();
        mypage = MyPage.getPageObject(pageBag.getPageIndex(), pageBag.getPageSize(), getCount());
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
        if (dataOrCount) {
            pageBag.setExecuteEnum(ExecuteEnum.PageQuery);
        } else {
            pageBag.setExecuteEnum(ExecuteEnum.PageCount);
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
            pageBag.setIsPage(false);
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
         String pre_sql = pageBag.getPreWith() + withSql;
         pageBag.setPreWith(pre_sql);
        return  this;
    }

    public PageQuery addPreWith(String withSql, Object... args) {
        if (CoreStringUtils.isEmpty(withSql))
            return  this;
            String pre_sql = pageBag.getPreWith() + withSql;
            pageBag.setPreWith(pre_sql);
            if (args != null && args.length > 0) {
                Arrays.stream(args).forEach(item -> {
                    pageBag.getDynamics().put(UUID.randomUUID().toString(), item);
                });
            }
        return  this;
    }

    /// <summary>
    /// execution mode
    /// </summary>
    /// <param name="excuteMode"></param>
    public PageQuery setExecuteMode(ExecuteMode executeMode) {
        pageBag.setExecuteMode(executeMode);
        return this;
    }
}
