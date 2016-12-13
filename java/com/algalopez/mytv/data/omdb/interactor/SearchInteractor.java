package com.algalopez.mytv.data.omdb.interactor;

import android.graphics.Bitmap;
import android.util.Log;

import com.algalopez.mytv.data.omdb.model.AEntity;
import com.algalopez.mytv.data.omdb.model.SearchEntity;
import com.algalopez.mytv.data.omdb.network.OMDBApi;
import com.algalopez.mytv.data.omdb.network.OMDBContract;
import com.algalopez.mytv.data.omdb.network.OMDBUrlBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/25/16
 */


public class SearchInteractor implements IInteractor<SearchEntity> {

    private final static String LOGTAG = "NetworkRepository";

    private String mSearchTerm;
    private String mFormat;
    private String mType;
    private String mYear;
    private String mPage;


    public SearchInteractor(String searchTerm, String format, String type, String year, String page){
        mSearchTerm = searchTerm;
        mFormat = format;
        mType = type;
        mYear = year;
        mPage = page;
    }

    @Override
    public SearchEntity run(){

        JSONObject searchJson;
        JSONArray searchListJson;
        JSONObject searchItemJson;

        // Data variables
        String dataTitle;
        String dataYear;
        String dataImdbID;
        String dataType;
        String dataPoster;
        Bitmap dataImage;

        // Create response object
        SearchEntity searchEntity = new SearchEntity();

        // Build OMDB URL
        URL searchURL = OMDBUrlBuilder.buildSearch(mSearchTerm, mFormat, mType, mYear, mPage);

        // Download json
        searchJson = OMDBApi.jsonDownload(searchURL);
        if (searchJson == null) {return searchEntity;}
        Log.d(LOGTAG, "Downloaded: " + searchJson.toString());

        // Check response is valid
        String kResponse = searchJson.optString(OMDBContract.SearchOMDB.KEY_RESPONSE);
        if (!OMDBContract.SearchOMDB.RESPONSE_TRUE.equals(kResponse)) {
            searchEntity.setType(AEntity.TYPE_ERROR);
            return searchEntity;
        }

        // Add response variables
        String kTotalResults = searchJson.optString(OMDBContract.SearchOMDB.KEY_TOTALRESULTS, "0");
        SearchEntity.Header newHeader = searchEntity.newHeader();

        newHeader.setPage(mPage);
        newHeader.setSearchTerm(mSearchTerm);
        newHeader.setTotalResults(kTotalResults);
        searchEntity.setHeader(newHeader);

        // Get Data
        try {
            searchListJson = searchJson.getJSONArray(OMDBContract.SearchOMDB.KEY_SEARCH);
        } catch (JSONException e) {
            Log.d(LOGTAG, "JsonException1 " + e);
            return searchEntity;
        }
        if (searchListJson == null) { return searchEntity; }


        // Add every value
        for (int j=0; j<searchListJson.length(); j++) {

            // Check if there is a SearchModel tag containing the search values
            try {
                searchItemJson = searchListJson.getJSONObject(j);
            } catch (JSONException e) {
                Log.d(LOGTAG, "JsonException2 " + e);
                return searchEntity;
            }
            if (searchItemJson == null) {
                Log.d(LOGTAG, "No SearchModel Items ");
                return searchEntity;
            }


            dataTitle = searchItemJson.optString(OMDBContract.SearchOMDB.KEY_TITLE);
            dataYear = searchItemJson.optString(OMDBContract.SearchOMDB.KEY_YEAR);
            dataImdbID = searchItemJson.optString(OMDBContract.SearchOMDB.KEY_IMDBID);
            dataType = searchItemJson.optString(OMDBContract.SearchOMDB.KEY_TYPE);
            dataPoster = searchItemJson.optString(OMDBContract.SearchOMDB.KEY_POSTER);
            dataImage = OMDBApi.imageDownload(OMDBUrlBuilder.buildImage(dataPoster));

            SearchEntity.Data newData = searchEntity.newData();
            newData.setTitle(dataTitle);
            newData.setYear(dataYear);
            newData.setImdbid(dataImdbID);
            newData.setType(dataType);
            newData.setPoster(dataPoster);
            newData.setImage(dataImage);
            searchEntity.appendData(newData);
        }

        return searchEntity;
    }

}
