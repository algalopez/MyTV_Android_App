package com.algalopez.mytv.data.omdb;

import android.util.Log;

import com.algalopez.mytv.data.omdb.converter.EpisodeConverter;
import com.algalopez.mytv.data.omdb.converter.SearchConverter;
import com.algalopez.mytv.data.omdb.converter.SeasonConverter;
import com.algalopez.mytv.data.omdb.converter.ShowConverter;
import com.algalopez.mytv.data.omdb.interactor.EpisodeInteractor;
import com.algalopez.mytv.data.omdb.interactor.SearchInteractor;
import com.algalopez.mytv.data.omdb.interactor.SeasonInteractor;
import com.algalopez.mytv.data.omdb.interactor.ShowInteractor;
import com.algalopez.mytv.data.omdb.network.OMDBUrlBuilder;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.model.ShowEntity;
import com.algalopez.mytv.domain.repository.INetworkRepository;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */


public class NetworkRepository implements INetworkRepository {

    private static final String LOGTAG = "NetworkRepository";

    public static final String DEFAULT_FORMAT = OMDBUrlBuilder.FORMAT_DEFAULT;
    public static final String DEFAULT_TYPE = OMDBUrlBuilder.TYPE_DEFAULT;
    public static final String DEFAULT_YEAR = OMDBUrlBuilder.YEAR_DEFAULT;
    public static final String DEFAULT_PAGE = OMDBUrlBuilder.PAGE_DEFAULT;
    public static final String DEFAULT_PLOT = OMDBUrlBuilder.PLOT_DEFAULT;
    public static final String DEFAULT_TOMATOES = OMDBUrlBuilder.TOMATOES_DEFAULT;


    @Override
    public ResponseModel<SearchEntity, ShowEntity> search(String searchTerm){
        Log.d(LOGTAG, "search(" + searchTerm + ")");
        return search(searchTerm, DEFAULT_FORMAT, DEFAULT_TYPE, DEFAULT_YEAR, DEFAULT_PAGE);
    }


    @Override
    public ResponseModel<SearchEntity, ShowEntity> search(String searchTerm, String format, String type, String year, String page){
        Log.d(LOGTAG, "search(" + searchTerm + ")");
        SearchConverter converter = new SearchConverter();
        SearchInteractor interactor = new SearchInteractor(searchTerm, format, type, year, page);
        return converter.convertToDomainModel(interactor.run());
    }


    @Override
    public ResponseModel<ShowEntity, SeasonEntity> getShow(String showID){
        Log.d(LOGTAG, "getShow(" + showID + ")");
        return getShow(showID, DEFAULT_FORMAT, DEFAULT_TYPE, DEFAULT_YEAR, DEFAULT_PLOT, DEFAULT_TOMATOES);
    }


    @Override
    public ResponseModel<ShowEntity, SeasonEntity> getShow(String showID, String format, String type, String year, String plot, String tomatoes){
        Log.d(LOGTAG, "getShow(" + showID + ")");
        ShowConverter converter = new ShowConverter();
        ShowInteractor interactor =  new ShowInteractor(showID, format, type, year, plot, tomatoes);
        return converter.convertToDomainModel(interactor.run());
    }


    @Override
    public ResponseModel<SeasonEntity, EpisodeEntity> getSeason(String showID, String season){
        Log.d(LOGTAG, "getSeason(" + showID + "," + season + ")");
        return getSeason(showID, season, DEFAULT_FORMAT, DEFAULT_TYPE, DEFAULT_YEAR, DEFAULT_PLOT, DEFAULT_TOMATOES);
    }


    @Override
    public ResponseModel<SeasonEntity, EpisodeEntity> getSeason(String showID, String season, String format, String type, String year, String plot, String tomatoes){
        Log.d(LOGTAG, "getSeason(" + showID + "," + season + ")");
        SeasonConverter converter = new SeasonConverter();
        SeasonInteractor interactor = new SeasonInteractor(showID, season, format, type, year, plot, tomatoes);
        return converter.convertToDomainModel(interactor.run());
    }


    @Override
    public ResponseModel<EpisodeEntity, ?> getEpisode(String showID, String season, String episode){
        Log.d(LOGTAG, "getEpisode(" + showID + "," + season + ", " + episode + ")");
        return getEpisode(showID, season, episode, DEFAULT_FORMAT, DEFAULT_TYPE, DEFAULT_YEAR, DEFAULT_PLOT, DEFAULT_TOMATOES);
    }


    @Override
    public ResponseModel<EpisodeEntity, ?> getEpisode(String showID, String season, String episode, String format, String type, String year, String plot, String tomatoes){
        Log.d(LOGTAG, "getEpisode(" + showID + "," + season + ", " + episode + ")");
        EpisodeConverter converter = new EpisodeConverter();
        EpisodeInteractor interactor = new EpisodeInteractor(showID, season, episode, format, type, year, plot, tomatoes);
        return converter.convertToDomainModel(interactor.run());
    }

}
