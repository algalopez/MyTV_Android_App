package com.algalopez.mytv.data.omdb.converter;


import com.algalopez.mytv.data.omdb.model.AEntity;
import com.algalopez.mytv.data.omdb.model.SearchEntity;
import com.algalopez.mytv.data.omdb.network.OMDBContract;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.ShowEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public class SearchConverter implements IConverter<ResponseModel<com.algalopez.mytv.domain.model.SearchEntity, ShowEntity>, SearchEntity> {


    @Override
    public ResponseModel<com.algalopez.mytv.domain.model.SearchEntity, ShowEntity> convertToDomainModel(SearchEntity omdb){


        ResponseModel<com.algalopez.mytv.domain.model.SearchEntity, ShowEntity> convertedData = new ResponseModel<>();

        SearchEntity.Header omdbHeader = omdb.getHeader();
        ArrayList<SearchEntity.Data> omdbData = omdb.getData();

        // Check data is not null
        if (omdbData == null) {
            omdbData = new ArrayList<>();
        }

        if (omdb.getType() == AEntity.TYPE_SEARCH){
            convertedData.setAction(ResponseModel.ActionType.SEARCH);
        } else {
            convertedData.setAction(ResponseModel.ActionType.ERROR);
        }

        // Set header
        if (omdbHeader != null) {
            com.algalopez.mytv.domain.model.SearchEntity newHeader = new com.algalopez.mytv.domain.model.SearchEntity();
            newHeader.setAction(com.algalopez.mytv.domain.model.AEntity.ActionType.SEARCH);
            newHeader.setSearchTerm(omdbHeader.getSearchTerm());
            newHeader.setTotalResults(omdbHeader.getTotalResults());
            newHeader.setPage(omdbHeader.getPage());
            convertedData.setHeader(newHeader);
        }

        // Set data
        for (SearchEntity.Data omdbDataItem: omdbData){
            if (omdbDataItem != null){
                com.algalopez.mytv.domain.model.ShowEntity newDataItem = new com.algalopez.mytv.domain.model.ShowEntity();
                newDataItem.setAction(com.algalopez.mytv.domain.model.AEntity.ActionType.SHOW);
                newDataItem.setTitle(omdbDataItem.getTitle());
                newDataItem.setYear(omdbDataItem.getYear());
                newDataItem.setImdbID(omdbDataItem.getImdbID());
                String type = omdbDataItem.getType();
                newDataItem.setPoster(omdbDataItem.getPoster());
                newDataItem.setImage(omdbDataItem.getImage());

                if (OMDBContract.ShowOMDB.TYPE_MOVIE.equals(type)){
                    newDataItem.setType(com.algalopez.mytv.domain.model.ShowEntity.TYPE.MOVIE);
                } else if (OMDBContract.ShowOMDB.TYPE_SERIES.equals(type)){
                    newDataItem.setType(com.algalopez.mytv.domain.model.ShowEntity.TYPE.SERIES);
                } else {
                    newDataItem.setType(com.algalopez.mytv.domain.model.ShowEntity.TYPE.NONE);
                }

                convertedData.appendData(newDataItem);
            }
        }

        return convertedData;
    }

}
