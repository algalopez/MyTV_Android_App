package com.algalopez.mytv.data.omdb.network;

import android.net.Uri;
import android.util.Log;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/21/16
 */


public class OMDBUrlBuilder {

    private final static String LOGTAG = "OMDBUrlBuilder";

    private static final String BASE_URL = "http://www.omdbapi.com/?";

    public static final String QUERY_PARAM = "t"; // query by title
    public static final String QUERYID_PARAM = "i"; // query by imdbid
    public static final String SEARCH_PARAM = "s";
    public static final String SEASON_PARAM = "season";
    public static final String EPISODE_PARAM = "episode";
    public static final String FORMAT_PARAM = "r";

    public static final String TYPE_PARAM = "type"; // Type of result to return: movie, series or episode
    public static final String YEAR_PARAM = "y"; // Year of release.
    public static final String PLOT_PARAM = "plot"; // Return short(default) or full plot.
    public static final String TOMATOES_PARAM = "tomatoes"; // Include Rotten Tomatoes ratings: true or false(default)
    public static final String PAGE_PARAM = "page"; // Page number to return.

    public static final String FORMAT_VALUE_JSON = "json";
    public static final String FORMAT_VALUE_XML = "xml";
    public static final String PLOT_VALUE_SHORT = "short";
    public static final String PLOT_VALUE_FULL = "full";
    public static final String TYPE_VALUE_MOVIE = "movie";
    public static final String TYPE_VALUE_SERIES = "series";
    public static final String TYPE_VALUE_EPISODE = "episode";
    public static final String TOMATOES_VALUE_TRUE = "true";
    public static final String TOMATOES_VALUE_FALSE = "false";

    public static final String TYPE_DEFAULT = "";
    public static final String YEAR_DEFAULT = "";
    public static final String FORMAT_DEFAULT = FORMAT_VALUE_JSON;
    public static final String PLOT_DEFAULT = PLOT_VALUE_SHORT;
    public static final String TOMATOES_DEFAULT = TOMATOES_VALUE_FALSE;
    public static final String PAGE_DEFAULT = "1";



    /*
     * ---------------------------------------------------------------------------------------------
     * SEARCH
     * ---------------------------------------------------------------------------------------------
     */

    /**
     *
     * @param searchTerm .
     * @return .
     */
    public static URL buildSearch(String searchTerm) {
        return buildSearch(searchTerm, FORMAT_DEFAULT, TYPE_DEFAULT, YEAR_DEFAULT, PAGE_DEFAULT);
    }

    /**
     *
     * @param searchTerm .
     * @param format .
     * @param type .
     * @param year .
     * @param page .
     * @return .
     */
    public static URL buildSearch(String searchTerm, String format, String type, String year, String page) {

        URL ret;
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(SEARCH_PARAM, searchTerm)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(TYPE_PARAM, type)
                .appendQueryParameter(YEAR_PARAM, year)
                .appendQueryParameter(PAGE_PARAM, page)
                .build();

        try {
            ret = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d(LOGTAG, "Malformed URL: " + e);
            return null;
        }
        return ret;
    }


    /*
     * ---------------------------------------------------------------------------------------------
     * MOVIE
     * ---------------------------------------------------------------------------------------------
     */


    /**
     *
     * @param showID .
     * @return .
     */
    public static URL buildShow(String showID) {
        return buildShow(showID, FORMAT_DEFAULT, TYPE_DEFAULT, YEAR_DEFAULT, PLOT_DEFAULT, TOMATOES_DEFAULT);
    }


    /**
     *
     * @param showID .
     * @param format .
     * @param type .
     * @param year .
     * @param plot .
     * @param tomatoes .
     * @return .
     */
    public static URL buildShow(String showID, String format, String type, String year, String plot, String tomatoes) {

        URL ret;
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERYID_PARAM, showID)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(TYPE_PARAM, type)
                .appendQueryParameter(YEAR_PARAM, year)
                .appendQueryParameter(PLOT_PARAM, plot)
                .appendQueryParameter(TOMATOES_PARAM, tomatoes)
                .build();

        try {
            ret = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d(LOGTAG, "Malformed URL: " + e);
            return null;
        }
        return ret;
    }


    /*
     * ---------------------------------------------------------------------------------------------
     * SEASON
     * ---------------------------------------------------------------------------------------------
     */


    /**
     *
     * @param showID .
     * @param season .
     * @return .
     */
    public static URL buildSeason(String showID, String season) {

        URL ret;
        try {
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERYID_PARAM, showID)
                    .appendQueryParameter(SEASON_PARAM, season)
                    .appendQueryParameter(FORMAT_PARAM, FORMAT_VALUE_JSON)
                    .build();

            ret = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            Log.d(LOGTAG, "Malformed URL: " + e);
            return null;
        }
        return ret;
    }


    /**
     *
     * @param showID .
     * @param season .
     * @param format .
     * @param type .
     * @param year .
     * @param plot .
     * @param tomatoes .
     * @return .
     */
    public static URL buildSeason(String showID, String season, String format, String type, String year, String plot, String tomatoes) {

        URL ret;
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERYID_PARAM, showID)
                .appendQueryParameter(SEASON_PARAM, season)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(TYPE_PARAM, type)
                .appendQueryParameter(YEAR_PARAM, year)
                .appendQueryParameter(PLOT_PARAM, plot)
                .appendQueryParameter(TOMATOES_PARAM, tomatoes)
                .build();

        try {
            ret = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d(LOGTAG, "Malformed URL: " + e);
            return null;
        }
        return ret;
    }


    /*
     * ---------------------------------------------------------------------------------------------
     * EPISODE
     * ---------------------------------------------------------------------------------------------
     */


    /**
     *
     * @param showID .
     * @param season .
     * @param episode .
     * @return .
     */
    public static URL buildEpisode(String showID, String season, String episode) {

        URL ret;
        try {
            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendQueryParameter(QUERYID_PARAM, showID)
                    .appendQueryParameter(SEASON_PARAM, season)
                    .appendQueryParameter(EPISODE_PARAM, episode)
                    .appendQueryParameter(FORMAT_PARAM, FORMAT_VALUE_JSON)
                    .build();

            ret = new URL(builtUri.toString());

        } catch (MalformedURLException e) {
            Log.d(LOGTAG, "Malformed URL: " + e);
            return null;
        }
        return ret;
    }


    /**
     *
     * @param showID .
     * @param season .
     * @param episode .
     * @param format .
     * @param type .
     * @param year .
     * @param plot .
     * @param tomatoes .
     * @return .
     */
    public static URL buildEpisode(String showID, String season, String episode, String format, String type, String year, String plot, String tomatoes) {

        URL ret;
        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(QUERYID_PARAM, showID)
                .appendQueryParameter(SEASON_PARAM, season)
                .appendQueryParameter(EPISODE_PARAM, episode)
                .appendQueryParameter(FORMAT_PARAM, format)
                .appendQueryParameter(TYPE_PARAM, type)
                .appendQueryParameter(YEAR_PARAM, year)
                .appendQueryParameter(PLOT_PARAM, plot)
                .appendQueryParameter(TOMATOES_PARAM, tomatoes)
                .build();

        try {
            ret = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            Log.d(LOGTAG, "Malformed URL: " + e);
            return null;
        }
        return ret;
    }


    /*
     * ---------------------------------------------------------------------------------------------
     * IMAGE
     * ---------------------------------------------------------------------------------------------
     */


    /**
     *
     * @param imageURLStr .
     * @return .
     */
    public static URL buildImage(String imageURLStr) {

        URL ret;
        try {
            ret = new URL(imageURLStr);
        } catch (MalformedURLException e) {
            Log.d(LOGTAG, "Malformed image URL: " + e);
            return null;
        }
        return ret;
    }
}
