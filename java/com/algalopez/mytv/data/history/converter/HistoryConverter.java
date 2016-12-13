package com.algalopez.mytv.data.history.converter;


import com.algalopez.mytv.domain.model.SearchEntity;

import java.util.ArrayList;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */


public class HistoryConverter {


    /**
     * Convert all searches from history model to domain model
     */
    public ArrayList<SearchEntity> convertAllToDomainModel(ArrayList<com.algalopez.mytv.data.history.model.HistoryEntity> data){

        ArrayList<SearchEntity> response = new ArrayList<>();
        for (com.algalopez.mytv.data.history.model.HistoryEntity dataItem: data){

            SearchEntity responseItem = new SearchEntity();
            responseItem.setTotalResults(dataItem.getTotalResults());
            responseItem.setSearchTerm(dataItem.getSearchTerm());
            responseItem.setPage("0");

            response.add(responseItem);
        }

        return response;
    }


    /**
     * Convert search from domain model to history model
     */
    public com.algalopez.mytv.data.history.model.HistoryEntity convertToDataModel(SearchEntity data){

        com.algalopez.mytv.data.history.model.HistoryEntity response = new com.algalopez.mytv.data.history.model.HistoryEntity();
        response.setSearchTerm(data.getSearchTerm());
        response.setTotalResults(data.getTotalResults());

        return response;
    }

}
