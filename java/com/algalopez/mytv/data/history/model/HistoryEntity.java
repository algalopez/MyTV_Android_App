package com.algalopez.mytv.data.history.model;

import com.algalopez.mytv.domain.model.AEntity;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */

public class HistoryEntity {
    private String searchTerm;
    private String totalResults;

    public String getSearchTerm() { return searchTerm; }
    public String getTotalResults() { return totalResults; }

    public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
    public void setTotalResults(String totalResults) { this.totalResults = totalResults; }

}
