package com.anderl.hibernate.ext;


public class PagingHelper {

    int pageSize = 20;
    int firstResultIndex = 0;

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getFirstResultIndex() {
        return firstResultIndex;
    }

    public void setFirstResultIndex(int firstResultIndex) {
        this.firstResultIndex = firstResultIndex;
    }
}
