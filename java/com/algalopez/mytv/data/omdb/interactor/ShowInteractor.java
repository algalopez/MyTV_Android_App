package com.algalopez.mytv.data.omdb.interactor;

import android.util.Log;

import com.algalopez.mytv.data.omdb.model.AEntity;
import com.algalopez.mytv.data.omdb.model.ShowEntity;
import com.algalopez.mytv.data.omdb.network.OMDBApi;
import com.algalopez.mytv.data.omdb.network.OMDBContract;
import com.algalopez.mytv.data.omdb.network.OMDBUrlBuilder;

import org.json.JSONObject;

import java.net.URL;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public class ShowInteractor implements IInteractor<ShowEntity> {

    private final static String LOGTAG = "NetworkRepository";

    private String mShowID;
    private String mFormat;
    private String mType;
    private String mYear;
    private String mPlot;
    private String mTomatoes;

    public ShowInteractor(String showID, String format, String type, String year, String plot, String tomatoes){
        mShowID = showID;
        mFormat = format;
        mType = type;
        mYear = year;
        mPlot = plot;
        mTomatoes = tomatoes;
    }


    @Override
    public ShowEntity run(){

        JSONObject showJson;

        // Create response object
        ShowEntity showEntity = new ShowEntity();

        // Build OMDB URL
        URL showURL = OMDBUrlBuilder.buildShow(mShowID, mFormat, mType, mYear, mPlot, mTomatoes);

        // Download json
        showJson = OMDBApi.jsonDownload(showURL);
        if (showJson == null) {return showEntity;}
        Log.d(LOGTAG, "Downloaded: " + showJson.toString());

        // Check response is valid
        String kResponse = showJson.optString(OMDBContract.ShowOMDB.KEY_RESPONSE);
        if (!OMDBContract.ShowOMDB.RESPONSE_TRUE.equals(kResponse)) {
            showEntity.setType(AEntity.TYPE_ERROR);
            return showEntity;
        }

        // Add head
        ShowEntity.Header newHeader = showEntity.newHeader();
        newHeader.setTitle(showJson.optString(OMDBContract.ShowOMDB.KEY_TITLE));
        newHeader.setYear(showJson.optString(OMDBContract.ShowOMDB.KEY_YEAR));
        newHeader.setRated(showJson.optString(OMDBContract.ShowOMDB.KEY_RATED));
        newHeader.setReleased(showJson.optString(OMDBContract.ShowOMDB.KEY_RELEASED));
        newHeader.setRuntime(showJson.optString(OMDBContract.ShowOMDB.KEY_RUNTIME));
        newHeader.setGenre(showJson.optString(OMDBContract.ShowOMDB.KEY_GENRE));
        newHeader.setDirector(showJson.optString(OMDBContract.ShowOMDB.KEY_DIRECTOR));
        newHeader.setWriter(showJson.optString(OMDBContract.ShowOMDB.KEY_WRITER));
        newHeader.setActors(showJson.optString(OMDBContract.ShowOMDB.KEY_ACTORS));
        newHeader.setPlot(showJson.optString(OMDBContract.ShowOMDB.KEY_PLOT));
        newHeader.setLanguage(showJson.optString(OMDBContract.ShowOMDB.KEY_LANGUAGE));
        newHeader.setCountry(showJson.optString(OMDBContract.ShowOMDB.KEY_COUNTRY));
        newHeader.setAwards(showJson.optString(OMDBContract.ShowOMDB.KEY_AWARDS));
        newHeader.setPoster(showJson.optString(OMDBContract.ShowOMDB.KEY_POSTER));
        newHeader.setMetascore(showJson.optString(OMDBContract.ShowOMDB.KEY_METASCORE));
        newHeader.setImdbRating(showJson.optString(OMDBContract.ShowOMDB.KEY_IMDBRATING));
        newHeader.setImdbVotes(showJson.optString(OMDBContract.ShowOMDB.KEY_IMDBVOTES));
        newHeader.setImdbID(showJson.optString(OMDBContract.ShowOMDB.KEY_IMDBID));
        newHeader.setType(showJson.optString(OMDBContract.ShowOMDB.KEY_TYPE));
        newHeader.setTotalSeasons(showJson.optString(OMDBContract.ShowOMDB.KEY_TOTALSEASONS, "0"));
        newHeader.setImage(OMDBApi.imageDownload(OMDBUrlBuilder.buildImage(newHeader.getPoster())));

        showEntity.setHeader(newHeader);

        // Add Data
        if (OMDBContract.ShowOMDB.TYPE_SERIES.equals(newHeader.getType())) {
            for (int j = 1; j <= Integer.valueOf(newHeader.getTotalSeasons()); j++){
                ShowEntity.Data newData = showEntity.newData();
                newData.setShowID(newHeader.getImdbID());
                newData.setSeason(String.valueOf(j));
                showEntity.appendData(newData);
            }
        }

        return showEntity;
    }

}
