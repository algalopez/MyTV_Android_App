package com.algalopez.mytv.domain.model;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */

public class HistoryEntity extends AEntity {

    private String searchTerm;
    private String totalResults;

    public HistoryEntity(){
        super.setAction(ActionType.HISTORY);
    }

    public String getSearchTerm() { return searchTerm; }
    public String getTotalResults() { return totalResults; }

    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
    public void setTotalResults(String totalResults) { this.totalResults = totalResults; }

}
