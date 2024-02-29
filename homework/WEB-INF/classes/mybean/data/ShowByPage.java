package mybean.data;

//import com.sun.rowset.*;   // 已被以下类包替代
import javax.sql.rowset.CachedRowSet;

public class ShowByPage {

    // CachedRowSetImpl rowSet=null; // CachedRowSetImpl 已被 CachedRowSet替代
    CachedRowSet rowSet = null;
    int pageSize = 10;
    int pageAllCount = 0;
    int showPage = 1;
    StringBuffer presentPageResult;

    // public CachedRowSetImpl getRowSet() {
    public CachedRowSet getRowSet() {
        return rowSet;
    }

    // public void setRowSet(CachedRowSetImpl rowSet) {
    public void setRowSet(CachedRowSet rowSet) {
        this.rowSet = rowSet;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageAllCount() {
        return pageAllCount;
    }

    public void setPageAllCount(int pageAllCount) {
        this.pageAllCount = pageAllCount;
    }

    public int getShowPage() {
        return showPage;
    }

    public void setShowPage(int showPage) {
        this.showPage = showPage;
    }

    public StringBuffer getPresentPageResult() {
        return presentPageResult;
    }

    public void setPresentPageResult(StringBuffer presentPageResult) {
        this.presentPageResult = presentPageResult;
    }

}
