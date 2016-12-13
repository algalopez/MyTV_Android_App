package com.algalopez.mytv.data.omdb.converter;

import com.algalopez.mytv.data.omdb.model.AEntity;
import com.algalopez.mytv.data.omdb.model.EpisodeEntity;
import com.algalopez.mytv.data.omdb.network.OMDBContract;
import com.algalopez.mytv.domain.presentation.ResponseModel;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public class EpisodeConverter implements IConverter<ResponseModel<com.algalopez.mytv.domain.model.EpisodeEntity, ?>, EpisodeEntity> {


    @Override
    public ResponseModel<com.algalopez.mytv.domain.model.EpisodeEntity, ?> convertToDomainModel(EpisodeEntity omdb) {


        ResponseModel<com.algalopez.mytv.domain.model.EpisodeEntity, ?> convertedData = new ResponseModel<>();

        EpisodeEntity.Header omdbHeader = omdb.getHeader();

        if (omdb.getType() == AEntity.TYPE_EPISODE){
            convertedData.setAction(ResponseModel.ActionType.EPISODE);
        } else {
            convertedData.setAction(ResponseModel.ActionType.ERROR);
        }

        // Set header
        if (omdbHeader != null){
            com.algalopez.mytv.domain.model.EpisodeEntity newHeader = new com.algalopez.mytv.domain.model.EpisodeEntity();
            newHeader.setAction(com.algalopez.mytv.domain.model.AEntity.ActionType.EPISODE);
            newHeader.setTitle(omdbHeader.getTitle());
            newHeader.setYear(omdbHeader.getYear());
            newHeader.setRated(omdbHeader.getRated());
            newHeader.setReleased(omdbHeader.getReleased());
            newHeader.setSeason(omdbHeader.getSeason());
            newHeader.setEpisode(omdbHeader.getEpisode());
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
            newHeader.setSeriesID(omdbHeader.getSeriesID());
            String type = omdbHeader.getType();

            if (OMDBContract.EpisodeOMDB.TYPE_EPISODE.equals(type)){
                newHeader.setType(com.algalopez.mytv.domain.model.EpisodeEntity.TYPE.EPISODE);
            }else {
                newHeader.setType(com.algalopez.mytv.domain.model.EpisodeEntity.TYPE.NONE);
            }

            convertedData.setHeader(newHeader);
        }

        return convertedData;
    }
}
