package io.github.davidchild.bitter.op.page;

public class MyPage {
    /** 当前页码 */
    public int currentPage = 0;
    /** 每页记录 */
    public int pageRecords = 10;
    /** 页码总数 */
    public boolean hasNextPage;
    public boolean hasPreviousPage;
    public int nextPage;
    public int previousPage;
    public int startRecord;
    public int totalPages;
    /** 总记录数 */
    public int totalRecords;
    public MyPage() {}
    /// <summary>
    /// Instantiated page object is applicable to mobile terminal/PC terminal
    /// </summary>
    /// <param name="currPage">The current page number</param>
    /// <param name="pageSize">Quantity per page</param>
    /// <param name="totalRecords">Total Records</param>
    /// <param name="isFistPageZero">Whether the current first page is 0</param>
    public MyPage(int currPage, int pageSize, int totalRecords) {
        MyPage pa = MyPage.getPageObject(currPage, pageSize, totalRecords);
        this.currentPage = pa.currentPage;
        this.pageRecords = pa.pageRecords;
        this.hasNextPage = pa.hasNextPage;
        this.hasPreviousPage = pa.hasPreviousPage;
        this.nextPage = pa.nextPage;
        this.previousPage = pa.previousPage;
        this.startRecord = pa.startRecord;
        this.totalPages = pa.totalPages;
        this.totalRecords = pa.totalRecords;
    }

    /// <summary>
    /// Get Page Object
    /// </summary>
    /// <param name="currentPage"></param>
    /// <param name="pageRecords"></param>
    /// <param name="totalRecords"></param>
    /// <returns></returns>
    public static MyPage getPageObject(int currentPage, int pageRecords, int totalRecords) {
        MyPage p = new MyPage();
        if (currentPage < 0) {
            return p;
        }
        if (pageRecords <= 0) {
            return p;
        }
        if (totalRecords <= 0) {
            return p;
        }

        // Current page number
        p.currentPage = currentPage;
        // Records per page
        p.pageRecords = pageRecords;
        // Total Records
        p.totalRecords = totalRecords;
        // Calculate the total number of pages

        if (totalRecords < pageRecords) {
            p.totalPages = totalRecords == 0 ? 0 : 1;
        } else {
            if ((totalRecords % pageRecords) == 0) {
                p.totalPages = (p.totalRecords / p.pageRecords);
            } else {
                p.totalPages = ((p.totalRecords / p.pageRecords) + 1);
            }
        }

        p.totalPages = (p.totalPages - 1);
        // Number of starting records
        p.startRecord = 0;
        // Judge whether the current page is the last page
        if ((currentPage < p.totalPages) && p.totalPages > 0) {
            // next page
            p.nextPage = currentPage + 1;
            // Whether there is the next page
            p.hasNextPage = true;
        } else {
            p.hasNextPage = false;
        }

        // Judge whether the current page is the first page
        if ((currentPage > 0) && (currentPage <= p.totalPages) && (p.totalPages > 0)) {
            // previous page
            p.previousPage = currentPage - 1;
            // Whether there is a previous page
            p.hasPreviousPage = true;
        } else {
            p.hasPreviousPage = false;
        }
        p.totalPages = (p.totalPages + 1);
        return p;
    }
}
