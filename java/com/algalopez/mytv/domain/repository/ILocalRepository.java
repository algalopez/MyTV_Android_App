package com.algalopez.mytv.domain.repository;

import android.net.Uri;

import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.model.ShowEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public interface ILocalRepository {

    ArrayList<ShowEntity> getAllFavourite();
    int removeAllFavourite();


    ArrayList<ShowEntity> getShow();
    ShowEntity getShow(String showID);
    Uri setShow(String showID, ShowEntity data);
    int updateShow(String showID, ShowEntity data);
    int removeShow(String showID);

    ArrayList<SeasonEntity> getSeason(String showID);
    SeasonEntity getSeason(String showID, String season);
    Uri setSeason(String showID, String season, SeasonEntity data);
    int updateSeason(String showID, String season, SeasonEntity data);
    int removeSeason(String showID, String season);

    ArrayList<EpisodeEntity> getEpisode(String showID, String season);
    EpisodeEntity getEpisode(String showID, String season, String episode);
    Uri setEpisode(String showID, String season, String episode, EpisodeEntity data);
    int updateEpisode(String showID, String season, String episode, EpisodeEntity data);
    int removeEpisode(String showID, String season, String episode);

}
