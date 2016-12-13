package com.algalopez.mytv.domain.repository;

import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.model.ShowEntity;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public interface INetworkRepository {


    ResponseModel<SearchEntity, ShowEntity> search(String searchTerm);
    ResponseModel<SearchEntity, ShowEntity> search(String searchTerm, String format, String type, String year, String page);

    ResponseModel<ShowEntity, SeasonEntity> getShow(String showID);
    ResponseModel<ShowEntity, SeasonEntity> getShow(String showID, String format, String type, String year, String plot, String tomatoes);

    ResponseModel<SeasonEntity, EpisodeEntity> getSeason(String showID, String season);
    ResponseModel<SeasonEntity, EpisodeEntity> getSeason(String showID, String season, String format, String type, String year, String plot, String tomatoes);

    ResponseModel<EpisodeEntity, ?> getEpisode(String showID, String season, String episode);
    ResponseModel<EpisodeEntity, ?> getEpisode(String showID, String season, String episode, String format, String type, String year, String plot, String tomatoes);
}
