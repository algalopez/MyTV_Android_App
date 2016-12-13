package com.algalopez.mytv.data.omdb.converter;

import com.algalopez.mytv.data.omdb.model.AEntity;
import com.algalopez.mytv.data.omdb.model.SeasonEntity;
import com.algalopez.mytv.data.omdb.util.dateUtil;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.EpisodeEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public class SeasonConverter implements IConverter<ResponseModel<com.algalopez.mytv.domain.model.SeasonEntity, EpisodeEntity>, SeasonEntity> {


    @Override
    public ResponseModel<com.algalopez.mytv.domain.model.SeasonEntity, EpisodeEntity> convertToDomainModel(SeasonEntity omdb) {

        com.algalopez.mytv.domain.model.SeasonEntity newHeader = new com.algalopez.mytv.domain.model.SeasonEntity();

        ResponseModel<com.algalopez.mytv.domain.model.SeasonEntity, EpisodeEntity> convertedData = new ResponseModel<>();
        SeasonEntity.Header omdbHeader = omdb.getHeader();
        ArrayList<SeasonEntity.Data> omdbData = omdb.getData();

        // Check data is not null
        if (omdbData == null) {
            omdbData = new ArrayList<>();
        }

        if (omdb.getType() == AEntity.TYPE_SEASON){
            convertedData.setAction(ResponseModel.ActionType.SEASON);
        } else {
            convertedData.setAction(ResponseModel.ActionType.ERROR);
        }

        // Set header
        if (omdbHeader != null) {
            newHeader.setAction(com.algalopez.mytv.domain.model.AEntity.ActionType.SEASON);
            newHeader.setTitle(omdbHeader.getTitle());
            newHeader.setSeason(omdbHeader.getSeason());
            newHeader.setTotalSeasons(omdbHeader.getTotalSeasons());
            newHeader.setShowID(omdbHeader.getShowID());
            newHeader.setTotalEpisodes(String.valueOf(omdbData.size()));
        }

        // Set data
        for (SeasonEntity.Data omdbDataItem: omdbData) {
            if (omdbDataItem != null) {

                String released = omdbDataItem.getReleased();
                if (dateUtil.isBefore(newHeader.getReleasedFirst(), released)){
                    newHeader.setReleasedFirst(released);
                }
                if (dateUtil.isAfter(newHeader.getReleasedLast(), released)){
                    newHeader.setReleasedLast(released);
                }

                com.algalopez.mytv.domain.model.EpisodeEntity newDataItem = new EpisodeEntity();
                newDataItem.setAction(com.algalopez.mytv.domain.model.AEntity.ActionType.EPISODE);
                newDataItem.setTitle(omdbDataItem.getTitle());
                newDataItem.setReleased(omdbDataItem.getReleased());
                newDataItem.setEpisode(omdbDataItem.getEpisode());
                newDataItem.setImdbRating(omdbDataItem.getImdbRating());
                newDataItem.setImdbID(omdbDataItem.getImdbID());
                newDataItem.setSeriesID(omdbDataItem.getShowID());
                newDataItem.setSeason(omdbDataItem.getSeason());

                convertedData.appendData(newDataItem);
            }
        }

        convertedData.setHeader(newHeader);

        return convertedData;
    }
}
