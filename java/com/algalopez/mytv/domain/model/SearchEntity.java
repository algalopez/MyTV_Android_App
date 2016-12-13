package com.algalopez.mytv.domain.model;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */

public class SearchEntity extends AEntity {

    private String searchTerm;
    private String totalResults;
    private String page;


    public SearchEntity(){
        super.setAction(ActionType.SEARCH);
    }

    public String getSearchTerm() { return searchTerm; }
    public String getTotalResults() { return totalResults; }
    public String getPage() { return page; }

    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
    public void setTotalResults(String totalResults) { this.totalResults = totalResults; }
    public void setPage(String page) { this.page = page; }

}
