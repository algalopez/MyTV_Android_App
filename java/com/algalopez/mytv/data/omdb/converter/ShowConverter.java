package com.algalopez.mytv.data.omdb.converter;

import com.algalopez.mytv.data.omdb.model.AEntity;
import com.algalopez.mytv.data.omdb.model.ShowEntity;
import com.algalopez.mytv.data.omdb.network.OMDBContract;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.SeasonEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public class ShowConverter implements IConverter<ResponseModel<com.algalopez.mytv.domain.model.ShowEntity, SeasonEntity>, ShowEntity>{

    @Override
    public ResponseModel<com.algalopez.mytv.domain.model.ShowEntity, SeasonEntity> convertToDomainModel(ShowEntity omdb){


        ResponseModel<com.algalopez.mytv.domain.model.ShowEntity, SeasonEntity> convertedData = new ResponseModel<>();

        ShowEntity.Header omdbHeader = omdb.getHeader();
        ArrayList<ShowEntity.Data> omdbData = omdb.getData();

        // Check data is not null
        if (omdbData == null) {
            omdbData = new ArrayList<>();
        }

        if (omdb.getType() == AEntity.TYPE_SHOW){
            convertedData.setAction(ResponseModel.ActionType.SHOW);
        } else {
            convertedData.setAction(ResponseModel.ActionType.ERROR);
        }

        // Set header
        if (omdbHeader != null) {
            com.algalopez.mytv.domain.model.ShowEntity newHeader = new com.algalopez.mytv.domain.model.ShowEntity();
            newHeader.setAction(com.algalopez.mytv.domain.model.AEntity.ActionType.SHOW);
            newHeader.setTitle(omdbHeader.getTitle());
            newHeader.setYear(omdbHeader.getYear());
            newHeader.setRated(omdbHeader.getRated());
            newHeader.setReleased(omdbHeader.getReleased());
            newHeader.setRuntime(omdbHeader.getRuntime());
            newHeader.setGenre(omdbHeader.getGenre());
            newHeader.setDirector(omdbHeader.getDirector());
            newHeader.setWriter(omdbHeader.getWriter());
            newHeader.setActors(omdbHeader.getActors());
            newHeader.setPlot(omdbHeader.getPlot());
            newHeader.setLanguage(omdbHeader.getLanguage());
            newHeader.setCountry(omdbHeader.getCountry());
            newHeader.setAwards(omdbHeader.getAwards());
            newHeader.setPoster(omdbHeader.getPoster());
            newHeader.setMetascore(omdbHeader.getMetascore());
            newHeader.setImdbRating(omdbHeader.getImdbRating());
            newHeader.setImdbVotes(omdbHeader.getImdbVotes());
            newHeader.setImdbID(omdbHeader.getImdbID());
            newHeader.setTotalSeasons(omdbHeader.getTotalSeasons());
            newHeader.setImage(omdbHeader.getImage());

            if (OMDBContract.ShowOMDB.TYPE_MOVIE.equals(omdbHeader.getType())){
                newHeader.setType(com.algalopez.mytv.domain.model.ShowEntity.TYPE.MOVIE);
            } else if (OMDBContract.ShowOMDB.TYPE_SERIES.equals(omdbHeader.getType())){
                newHeader.setType(com.algalopez.mytv.domain.model.ShowEntity.TYPE.SERIES);
            } else {
                newHeader.setType(com.algalopez.mytv.domain.model.ShowEntity.TYPE.NONE);
            }
            convertedData.setHeader(newHeader);
        }

        // Set data
        for (ShowEntity.Data omdbDataItem: omdbData){
            if(omdbDataItem != null){
                com.algalopez.mytv.domain.model.SeasonEntity newDataItem = new SeasonEntity();
                newDataItem.setAction(com.algalopez.mytv.domain.model.AEntity.ActionType.SEASON);
                newDataItem.setShowID(omdbDataItem.getShowID());
                newDataItem.setSeason(omdbDataItem.getSeason());

                convertedData.appendData(newDataItem);
            }
        }

        return convertedData;
    }

}
