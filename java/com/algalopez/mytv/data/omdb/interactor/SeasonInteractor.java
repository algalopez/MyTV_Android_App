package com.algalopez.mytv.data.omdb.interactor;


import android.util.Log;

import com.algalopez.mytv.data.omdb.model.AEntity;
import com.algalopez.mytv.data.omdb.model.SeasonEntity;
import com.algalopez.mytv.data.omdb.network.OMDBApi;
import com.algalopez.mytv.data.omdb.network.OMDBContract.SeasonOMDB;
import com.algalopez.mytv.data.omdb.network.OMDBUrlBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public class SeasonInteractor implements IInteractor<SeasonEntity> {

    private final static String LOGTAG = "NetworkRepository";

    private String mShowID;
    private String mSeason;
    private String mFormat;
    private String mType;
    private String mYear;
    private String mPlot;
    private String mTomatoes;


    public SeasonInteractor(String showID, String season, String format, String type, String year, String plot, String tomatoes){
        mShowID = showID;
        mSeason = season;
        mFormat = format;
        mType = type;
        mYear = year;
        mPlot = plot;
        mTomatoes = tomatoes;
    }


    @Override
    public SeasonEntity run() {

        JSONObject seasonJson;
        JSONArray episodeArray = null;
        JSONObject episodeJson;

        int totalEpisodes;

        // Create response object
        SeasonEntity seasonEntity = new SeasonEntity();
        SeasonEntity.Header newHeader = seasonEntity.newHeader();

        // Build OMDB URL
        URL seasonURL = OMDBUrlBuilder.buildSeason(mShowID, mSeason, mFormat, mType, mYear, mPlot, mTomatoes);

        // Download json
        seasonJson = OMDBApi.jsonDownload(seasonURL);
        if (seasonJson == null) {return seasonEntity;}
        Log.d(LOGTAG, "Downloaded season: " + seasonJson.toString());

        // Check response is valid
        String kResponse = seasonJson.optString(SeasonOMDB.KEY_RESPONSE);
        if (SeasonOMDB.RESPONSE_FALSE.equals(kResponse)) {
            seasonEntity.setType(AEntity.TYPE_ERROR);
            return seasonEntity;
        }

        // Add Entity Head
        newHeader.setShowID(mShowID);
        newHeader.setTitle(seasonJson.optString(SeasonOMDB.KEY_S_TITLE));
        newHeader.setSeason(seasonJson.optString(SeasonOMDB.KEY_S_SEASON, mSeason));
        newHeader.setTotalSeasons(seasonJson.optString(SeasonOMDB.KEY_S_TOTALSEASONS, "0"));
        seasonEntity.setHeader(newHeader);

        // Check if data is valid
        try {
            episodeArray = seasonJson.getJSONArray(SeasonOMDB.KEY_S_EPISODES);
            totalEpisodes = episodeArray.length();
        } catch (JSONException e) {
            totalEpisodes = 0;
            episodeArray = null;
        }

        // Add Entity Data
        for (int j = 0; j < totalEpisodes; j++){

            try {
                episodeJson = episodeArray.getJSONObject(j);
            } catch (JSONException e){
                episodeJson = null;
            }

            if (episodeJson != null) {

                SeasonEntity.Data newData = seasonEntity.newData();

                newData.setTitle(episodeJson.optString(SeasonOMDB.KEY_E_TITLE));
                newData.setReleased(episodeJson.optString(SeasonOMDB.KEY_E_RELEASED));
                newData.setEpisode(episodeJson.optString(SeasonOMDB.KEY_E_EPISODE));
                newData.setImdbRating(episodeJson.optString(SeasonOMDB.KEY_E_IMDBRATING));
                newData.setImdbID(episodeJson.optString(SeasonOMDB.KEY_E_IMDBID));
                newData.setShowID(mShowID);
                newData.setSeason(mSeason);
                seasonEntity.appendData(newData);
            }
        }

        return seasonEntity;
    }
}
