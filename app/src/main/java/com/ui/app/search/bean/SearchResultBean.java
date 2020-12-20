package com.ui.app.search.bean;

/**
 * Created by Administrator on 2018/12/16 0016.
 */

public class SearchResultBean {
    Object results;
    String currentSourceSite;
//     int  currentPage;

    public Object getResults() {
        return results;
    }

    public void setResults(Object results) {
        this.results = results;
    }

    public String getCurrentSourceSite() {
        return currentSourceSite;
    }

    public void setCurrentSourceSite(String currentSourceSite) {
        this.currentSourceSite = currentSourceSite;
    }

//    public int getCurrentPage() {
//        return currentPage;
//    }
//
//    public void setCurrentPage(int currentPage) {
//        this.currentPage = currentPage;
//    }
}
