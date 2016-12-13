package com.algalopez.mytv.data.local.database;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    9/14/16
 */

public class MyTVDbContract {

    private final static String LOGTAG = "MyTVDbContract";


    public static final String CONTENT_AUTHORITY = "com.algalopez.mytv";
    private static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public final static String PATH_FAVOURITE = "favourite";
    public final static String PATH_SHOW = "show";
    public final static String PATH_SEASON = "season";
    public final static String PATH_EPISODE = "episode";


    public static final class ShowEntry implements BaseColumns {

        public static final String TABLE_NAME = "show";

        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_YEAR = "Year";
        public static final String COLUMN_RATED = "Rated";
        public static final String COLUMN_RELEASED = "Released";
        public static final String COLUMN_RUNTIME = "Runtime";
        public static final String COLUMN_GENRE = "Genre";
        public static final String COLUMN_DIRECTOR = "Director";
        public static final String COLUMN_WRITER = "Writer";
        public static final String COLUMN_ACTORS = "Actors";
        public static final String COLUMN_PLOT = "Plot";
        public static final String COLUMN_LANGUAGE = "Language";
        public static final String COLUMN_COUNTRY = "Country";
        public static final String COLUMN_AWARDS = "Awards";
        public static final String COLUMN_POSTER = "Poster";
        public static final String COLUMN_METASCORE = "Metascore";
        public static final String COLUMN_IMDBRATING = "imdbRating";
        public static final String COLUMN_IMDBVOTES = "imdbVotes";
        public static final String COLUMN_IMDBID = "imdbID";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_TOTALSEASONS = "totalSeasons";
        public static final String COLUMN_IMAGE = "Image";

        public static final String KEY_RESPONSE = "Response";

        public static final String KEY_MOVIE = "movie";
        public static final String KEY_SERIES = "series";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SHOW;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SHOW;


        public static Uri buildFavourite() {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_FAVOURITE)
                    .build();
        }


        public static Uri buildShow(String show) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_SHOW)
                    .appendPath(show)
                    .build();
        }


        // Every Column in the database show table
        public static String[] getFavouriteProjection() {
            return new String[]{_ID, COLUMN_TITLE, COLUMN_YEAR, COLUMN_RATED,
                    COLUMN_RELEASED, COLUMN_RUNTIME, COLUMN_GENRE, COLUMN_DIRECTOR, COLUMN_WRITER,
                    COLUMN_ACTORS, COLUMN_PLOT, COLUMN_LANGUAGE, COLUMN_COUNTRY, COLUMN_AWARDS,
                    COLUMN_POSTER, COLUMN_METASCORE, COLUMN_IMDBRATING, COLUMN_IMDBVOTES,
                    COLUMN_IMDBID, COLUMN_TYPE, COLUMN_TOTALSEASONS, COLUMN_IMAGE};
        }


        public static String getShowFromUri(Uri uri){

            return uri.getPathSegments().get(1);
        }
    }


    public static final class SeasonEntry implements BaseColumns {

        public static final String TABLE_NAME = "season";

        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_SEASON = "Season";
        public static final String COLUMN_TOTALSEASONS = "totalSeasons";

        public static final String COLUMN_TOTALEPISODES = "totalEpisodes";
        public static final String COLUMN_RELEASEDFIRST = "releasedFirst";
        public static final String COLUMN_RELEASEDLAST = "releasedLast";

        public static final String COLUMN_SHOWID = "showID";


        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEASON;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_SEASON;


        public static Uri buildSeason(String show) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_SHOW)
                    .appendPath(show)
                    .appendPath(PATH_SEASON)
                    .build();
        }


        public static Uri buildSeason(String show, String season) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_SHOW)
                    .appendPath(show)
                    .appendPath(PATH_SEASON)
                    .appendPath(season)
                    .build();
        }


        public static String[] getSeasonProjection() {

            return new String[]{_ID, COLUMN_TITLE, COLUMN_SEASON, COLUMN_TOTALSEASONS,
                    COLUMN_TOTALEPISODES, COLUMN_RELEASEDFIRST, COLUMN_RELEASEDLAST,COLUMN_SHOWID};
        }


        public static String getShowFromUri(Uri uri){

            return uri.getPathSegments().get(1);
        }


        public static String getSeasonFromUri(Uri uri){

            return uri.getPathSegments().get(3);
        }
    }


    public static final class EpisodeEntry implements BaseColumns {

        public static final String TABLE_NAME = "episode";

        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_YEAR = "Year";
        public static final String COLUMN_RATED = "Rated";
        public static final String COLUMN_RELEASED = "Released";

        public static final String COLUMN_SEASON = "Season";
        public static final String COLUMN_EPISODE = "Episode";

        public static final String COLUMN_RUNTIME = "Runtime";
        public static final String COLUMN_GENRE = "Genre";
        public static final String COLUMN_DIRECTOR = "Director";
        public static final String COLUMN_WRITER = "Writer";
        public static final String COLUMN_ACTORS = "Actors";
        public static final String COLUMN_PLOT = "Plot";
        public static final String COLUMN_LANGUAGE = "Language";
        public static final String COLUMN_COUNTRY = "Country";
        public static final String COLUMN_AWARDS = "Awards";
        public static final String COLUMN_POSTER = "Poster";
        public static final String COLUMN_METASCORE = "Metascore";
        public static final String COLUMN_IMDBRATING = "imdbRating";
        public static final String COLUMN_IMDBVOTES = "imdbVotes";
        public static final String COLUMN_IMDBID = "imdbID";
        public static final String COLUMN_SERIESID = "SeriesID";
        public static final String COLUMN_TYPE = "Type";

        public static final String KEY_EPISODE = "episode";

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EPISODE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_EPISODE;


        public static Uri buildEpisode(String show, String season) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_SHOW)
                    .appendPath(show)
                    .appendPath(PATH_SEASON)
                    .appendPath(season)
                    .appendPath(PATH_EPISODE)
                    .build();
        }


        public static Uri buildEpisode(String show, String season, String episode) {
            return BASE_CONTENT_URI.buildUpon()
                    .appendPath(PATH_SHOW)
                    .appendPath(show)
                    .appendPath(PATH_SEASON)
                    .appendPath(season)
                    .appendPath(PATH_EPISODE)
                    .appendPath(episode)
                    .build();
        }


        public static String[] getEpisodeProjection() {
            return new String[]{_ID, COLUMN_TITLE, COLUMN_YEAR, COLUMN_RATED,
                    COLUMN_RELEASED, COLUMN_SEASON, COLUMN_EPISODE,COLUMN_RUNTIME, COLUMN_GENRE, COLUMN_DIRECTOR,
                    COLUMN_WRITER, COLUMN_ACTORS, COLUMN_PLOT, COLUMN_LANGUAGE, COLUMN_COUNTRY, COLUMN_AWARDS,
                    COLUMN_POSTER, COLUMN_METASCORE, COLUMN_IMDBRATING, COLUMN_IMDBVOTES,
                    COLUMN_IMDBID, COLUMN_SERIESID, COLUMN_TYPE};
        }


        public static String getShowFromUri(Uri uri){

            return uri.getPathSegments().get(1);
        }


        public static String getSeasonFromUri(Uri uri){

            return uri.getPathSegments().get(3);
        }


        public static String getEpisodeFromUri(Uri uri){

            return uri.getPathSegments().get(5);
        }
    }
}
