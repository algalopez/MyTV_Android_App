package com.algalopez.mytv.data.local;

import android.content.ContentResolver;
import android.net.Uri;
import android.util.Log;

import com.algalopez.mytv.data.local.converter.EpisodeConverter;
import com.algalopez.mytv.data.local.converter.IConverter;
import com.algalopez.mytv.data.local.converter.SeasonConverter;
import com.algalopez.mytv.data.local.converter.ShowConverter;
import com.algalopez.mytv.data.local.interactor.EpisodeInteractor;
import com.algalopez.mytv.data.local.interactor.FavouriteInteractor;
import com.algalopez.mytv.data.local.interactor.SeasonInteractor;
import com.algalopez.mytv.data.local.interactor.ShowInteractor;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.model.ShowEntity;
import com.algalopez.mytv.domain.repository.ILocalRepository;

import java.util.ArrayList;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */


public class LocalRepository implements ILocalRepository {

    private static final String LOGTAG = "LocalRepository";

    private ContentResolver mContentResolver;
    private String mSortOrder = null;


    public LocalRepository(ContentResolver contentResolver){

        mContentResolver = contentResolver;
    }

    public LocalRepository(ContentResolver contentResolver, String sortOrder){
        mContentResolver = contentResolver;
        this.mSortOrder = sortOrder;
    }


    // ---------------------------------------------------------------------------------------------
    // FAVOURITE
    // ---------------------------------------------------------------------------------------------


    @Override
    public ArrayList<ShowEntity> getAllFavourite() {
        Log.d(LOGTAG, "getAllFavourite()");
        IConverter<ShowEntity> converter = new ShowConverter();
        FavouriteInteractor.getAllFavouriteInteractor interactor = new FavouriteInteractor(mContentResolver).new getAllFavouriteInteractor(mSortOrder);
        return converter.convertAllToDomainModel(interactor.run());
    }


    @Override
    public int removeAllFavourite() {
        Log.d(LOGTAG, "removeAllFavourite()");
        FavouriteInteractor.removeAllFavouriteInteractor interactor = new FavouriteInteractor(mContentResolver).new removeAllFavouriteInteractor();
        return interactor.run();
    }


    // ---------------------------------------------------------------------------------------------
    // SHOW
    // ---------------------------------------------------------------------------------------------


    @Override
    public ArrayList<ShowEntity> getShow() {
        Log.d(LOGTAG, "getShow()");
        IConverter<ShowEntity> converter = new ShowConverter();
        ShowInteractor.getAllShow interactor = new ShowInteractor(mContentResolver).new getAllShow(mSortOrder);
        return converter.convertAllToDomainModel(interactor.run());
    }


    @Override
    public ShowEntity getShow(String showID) {
        Log.d(LOGTAG, "getShow("+showID+")");
        IConverter<ShowEntity> converter = new ShowConverter();
        ShowInteractor.getShow interactor = new ShowInteractor(mContentResolver).new getShow(showID, mSortOrder);
        return converter.convertToDomainModel(interactor.run());
    }


    @Override
    public Uri setShow(String showID, ShowEntity data) {
        Log.d(LOGTAG, "setShow("+showID+", data)");
        ShowConverter converter = new ShowConverter();
        ShowInteractor.setShow interactor = new ShowInteractor(mContentResolver).new setShow(showID, converter.convertToDataModel(data));
        return interactor.run();
    }


    @Override
    public int updateShow(String showID, ShowEntity data) {
        Log.d(LOGTAG, "updateShow("+showID+", data)");
        ShowConverter converter = new ShowConverter();
        ShowInteractor.updateShow interactor = new ShowInteractor(mContentResolver).new updateShow(showID, converter.convertToDataModel(data));
        return interactor.run();
    }


    @Override
    public int removeShow(String showID) {
        Log.d(LOGTAG, "removeShow("+showID+", data)");
        ShowInteractor.removeShow interactor = new ShowInteractor(mContentResolver).new removeShow(showID);
        return interactor.run();
    }


    // ---------------------------------------------------------------------------------------------
    // SEASON
    // ---------------------------------------------------------------------------------------------


    @Override
    public ArrayList<SeasonEntity> getSeason(String showID) {
        Log.d(LOGTAG, "getSeason("+showID+")");
        IConverter<SeasonEntity> converter = new SeasonConverter();
        SeasonInteractor.getAllSeason interactor = new SeasonInteractor(mContentResolver).new getAllSeason(showID, mSortOrder);
        return converter.convertAllToDomainModel(interactor.run());
    }


    @Override
    public SeasonEntity getSeason(String showID, String season) {
        Log.d(LOGTAG, "getSeason("+showID+", "+season+")");
        IConverter<SeasonEntity> converter = new SeasonConverter();
        SeasonInteractor.getSeason interactor = new SeasonInteractor(mContentResolver).new getSeason(showID, season, mSortOrder);
        return converter.convertToDomainModel(interactor.run());
    }


    @Override
    public Uri setSeason(String showID, String season, SeasonEntity data) {
        Log.d(LOGTAG, "setSeason("+showID+", "+season+", data)");
        SeasonConverter converter = new SeasonConverter();
        SeasonInteractor.setSeason interactor = new SeasonInteractor(mContentResolver).new setSeason(showID, season, converter.convertToDataModel(data));
        return interactor.run();
    }


    @Override
    public int updateSeason(String showID, String season, SeasonEntity data) {
        Log.d(LOGTAG, "updateSeason("+showID+", "+season+", data)");
        SeasonConverter converter = new SeasonConverter();
        SeasonInteractor.updateSeason interactor = new SeasonInteractor(mContentResolver).new updateSeason(showID, season, converter.convertToDataModel(data));
        return interactor.run();
    }


    @Override
    public int removeSeason(String showID, String season) {
        Log.d(LOGTAG, "removeSeason("+showID+", "+season+")");
        SeasonInteractor.removeSeason interactor = new SeasonInteractor(mContentResolver).new removeSeason(showID, season);
        return interactor.run();
    }


    // ---------------------------------------------------------------------------------------------
    // EPISODE
    // ---------------------------------------------------------------------------------------------


    @Override
    public ArrayList<EpisodeEntity> getEpisode(String showID, String season) {
        Log.d(LOGTAG, "getEpisode("+showID+", "+season+")");
        IConverter<EpisodeEntity> converter = new EpisodeConverter();
        EpisodeInteractor.getAllEpisode interactor = new EpisodeInteractor(mContentResolver).new getAllEpisode(showID, season, mSortOrder);
        return converter.convertAllToDomainModel(interactor.run());
    }


    @Override
    public EpisodeEntity getEpisode(String showID, String season, String episode) {
        Log.d(LOGTAG, "getEpisode("+showID+", "+season+", "+episode+")");
        IConverter<EpisodeEntity> converter = new EpisodeConverter();
        EpisodeInteractor.getEpisode interactor = new EpisodeInteractor(mContentResolver).new getEpisode(showID, season, episode, mSortOrder);
        return converter.convertToDomainModel(interactor.run());
    }


    @Override
    public Uri setEpisode(String showID, String season, String episode, EpisodeEntity data) {
        Log.d(LOGTAG, "setEpisode("+showID+", "+season+", "+episode+", data)");
        EpisodeConverter converter = new EpisodeConverter();
        EpisodeInteractor.setEpisode interactor = new EpisodeInteractor(mContentResolver).new setEpisode(showID, season, episode, converter.convertToDataModel(data));
        return interactor.run();
    }


    @Override
    public int updateEpisode(String showID, String season, String episode, EpisodeEntity data) {
        Log.d(LOGTAG, "updateEpisode("+showID+", "+season+", "+episode+", data)");
        EpisodeConverter converter = new EpisodeConverter();
        EpisodeInteractor.updateEpisode interactor = new EpisodeInteractor(mContentResolver).new updateEpisode(showID, season, episode, converter.convertToDataModel(data));
        return interactor.run();
    }


    @Override
    public int removeEpisode(String showID, String season, String episode) {
        Log.d(LOGTAG, "removeEpisode("+showID+", "+season+", "+episode+")");
        EpisodeInteractor.removeEpisode interactor = new EpisodeInteractor(mContentResolver).new removeEpisode(showID, season, episode);
        return interactor.run();
    }

}
