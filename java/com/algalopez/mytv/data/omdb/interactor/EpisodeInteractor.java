package com.algalopez.mytv.data.omdb.interactor;

import android.util.Log;

import com.algalopez.mytv.data.omdb.model.AEntity;
import com.algalopez.mytv.data.omdb.model.EpisodeEntity;
import com.algalopez.mytv.data.omdb.network.OMDBApi;
import com.algalopez.mytv.data.omdb.network.OMDBContract;
import com.algalopez.mytv.data.omdb.network.OMDBUrlBuilder;

import org.json.JSONObject;

import java.net.URL;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public class EpisodeInteractor implements IInteractor<EpisodeEntity> {

    private final static String LOGTAG = "NetworkRepository";

    private String mShowID;
    private String mSeason;
    private String mEpisode;
    private String mFormat;
    private String mType;
    private String mYear;
    private String mPlot;
    private String mTomatoes;

    public EpisodeInteractor(String showID, String season, String episode, String format, String type, String year, String plot, String tomatoes){
        mShowID = showID;
        mSeason = season;
        mEpisode = episode;
        mFormat = format;
        mType = type;
        mYear = year;
        mPlot = plot;
        mTomatoes = tomatoes;
    }

    @Override
    public EpisodeEntity run() {

        JSONObject episodeJson;

        // Create response object
        EpisodeEntity episodeEntity = new EpisodeEntity();

        // Build OMDB URL
        URL episodeURL = OMDBUrlBuilder.buildEpisode(mShowID,mSeason,mEpisode,mFormat,mType,mYear,mPlot,mTomatoes);

        // Download json
        episodeJson = OMDBApi.jsonDownload(episodeURL);
        if (episodeJson == null) {return episodeEntity;}
        Log.d(LOGTAG, "Downloaded episode: " + episodeJson.toString());

        // Check response is valid
        String kResponse = episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_RESPONSE);
        if (!OMDBContract.EpisodeOMDB.RESPONSE_TRUE.equals(kResponse)) {
            episodeEntity.setType(AEntity.TYPE_ERROR);
            return episodeEntity;
        }

        // Add Entity Header
        EpisodeEntity.Header newHeader = episodeEntity.newHeader();

        newHeader.setTitle(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_TITLE));
        newHeader.setYear(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_YEAR));
        newHeader.setRated(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_RATED));
        newHeader.setReleased(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_RELEASED));
        newHeader.setSeason(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_SEASON));
        newHeader.setEpisode(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_EPISODE));
        newHeader.setRuntime(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_RUNTIME));
        newHeader.setGenre(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_GENRE));
        newHeader.setDirector(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_DIRECTOR));
        newHeader.setWriter(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_WRITER));
        newHeader.setActors(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_ACTORS));
        newHeader.setPlot(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_PLOT));
        newHeader.setLanguage(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_LANGUAGE));
        newHeader.setCountry(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_COUNTRY));
        newHeader.setAwards(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_AWARDS));
        newHeader.setPoster(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_POSTER));
        newHeader.setMetascore(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_METASCORE));
        newHeader.setImdbRating(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_IMDBRATING));
        newHeader.setImdbVotes(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_IMDBVOTES));
        newHeader.setImdbID(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_IMDBID));
        newHeader.setSeriesID(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_SERIESID));
        newHeader.setType(episodeJson.optString(OMDBContract.EpisodeOMDB.KEY_TYPE));
        // TODO: Add tomatoes rating ?
        episodeEntity.setHeader(newHeader);


        return episodeEntity;
    }
}
