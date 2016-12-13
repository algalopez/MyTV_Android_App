package com.algalopez.mytv.data.omdb.network;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    9/30/16
 */


public class OMDBContract {

    private final static String LOGTAG = "OMDBContract";

    public static final class ShowOMDB {

        public static final String KEY_TITLE = "Title";
        public static final String KEY_YEAR = "Year";
        public static final String KEY_RATED = "Rated";
        public static final String KEY_RELEASED = "Released";
        public static final String KEY_RUNTIME = "Runtime";
        public static final String KEY_GENRE = "Genre";
        public static final String KEY_DIRECTOR = "Director";
        public static final String KEY_WRITER = "Writer";
        public static final String KEY_ACTORS = "Actors";
        public static final String KEY_PLOT = "Plot";
        public static final String KEY_LANGUAGE = "Language";
        public static final String KEY_COUNTRY = "Country";
        public static final String KEY_AWARDS = "Awards";
        public static final String KEY_POSTER = "Poster";
        public static final String KEY_METASCORE = "Metascore";
        public static final String KEY_IMDBRATING = "imdbRating";
        public static final String KEY_IMDBVOTES = "imdbVotes";
        public static final String KEY_IMDBID = "imdbID";
        public static final String KEY_TYPE = "Type";
        public static final String KEY_TOTALSEASONS = "totalSeasons";
        public static final String KEY_RESPONSE = "Response";

        public static final String TYPE_MOVIE = "movie";
        public static final String TYPE_SERIES = "series";
        public static final String TYPE_EPISODE = "episode";

        public static final String RESPONSE_TRUE = "True";
        public static final String RESPONSE_FALSE = "False";

        public static String[] getShowProjection(){

            return new String[] {KEY_TITLE, KEY_YEAR, KEY_RATED, KEY_RELEASED, KEY_RUNTIME, KEY_GENRE, KEY_DIRECTOR,
                    KEY_WRITER, KEY_ACTORS, KEY_PLOT, KEY_LANGUAGE, KEY_COUNTRY, KEY_AWARDS, KEY_POSTER, KEY_METASCORE,
                    KEY_IMDBRATING, KEY_IMDBVOTES, KEY_IMDBID, KEY_TYPE, KEY_TOTALSEASONS, KEY_RESPONSE};
        }

    }


    public static final class SeasonOMDB {
        public static final String KEY_S_TITLE = "Title";
        public static final String KEY_S_SEASON = "Season";
        public static final String KEY_S_TOTALSEASONS = "totalSeasons";
        public static final String KEY_S_RESPONSE = "Response";
        public static final String KEY_S_EPISODES = "Episodes";

        public static final String KEY_E_TITLE = "Title";
        public static final String KEY_E_RELEASED = "Released";
        public static final String KEY_E_EPISODE = "Episode";
        public static final String KEY_E_IMDBRATING = "imdbRating";
        public static final String KEY_E_IMDBID = "imdbId";

        public static final String KEY_RESPONSE = "Response";

        public static final String RESPONSE_TRUE = "True";
        public static final String RESPONSE_FALSE = "False";

        public static String[][] getShowProjection(){

            String[] season = new String[]{KEY_S_TITLE, KEY_S_SEASON, KEY_S_TOTALSEASONS, KEY_S_RESPONSE, KEY_S_EPISODES};
            String[] episode = new String[]{KEY_E_TITLE, KEY_E_RELEASED, KEY_E_EPISODE, KEY_E_IMDBRATING, KEY_E_IMDBID};
            return new String[][] {season, episode};
        }
    }


    public static final class EpisodeOMDB {

        public static final String KEY_TITLE = "Title";
        public static final String KEY_YEAR = "Year";
        public static final String KEY_RATED = "Rated";
        public static final String KEY_RELEASED = "Released";
        public static final String KEY_SEASON = "Season";
        public static final String KEY_EPISODE = "Episode";
        public static final String KEY_RUNTIME = "Runtime";
        public static final String KEY_GENRE = "Genre";
        public static final String KEY_DIRECTOR = "Director";
        public static final String KEY_WRITER = "Writer";
        public static final String KEY_ACTORS = "Actors";
        public static final String KEY_PLOT = "Plot";
        public static final String KEY_LANGUAGE = "Language";
        public static final String KEY_COUNTRY = "Country";
        public static final String KEY_AWARDS = "Awards";
        public static final String KEY_POSTER = "Poster";
        public static final String KEY_METASCORE = "Metascore";
        public static final String KEY_IMDBRATING = "imdbRating";
        public static final String KEY_IMDBVOTES = "imdbVotes";
        public static final String KEY_IMDBID = "imdbID";
        public static final String KEY_SERIESID = "seriesID";
        public static final String KEY_TYPE = "Type";
        public static final String KEY_RESPONSE = "Response";

        public static final String RESPONSE_TRUE = "True";
        public static final String RESPONSE_FALSE = "False";

        public static final String TYPE_MOVIE = "movie";
        public static final String TYPE_SERIES = "series";
        public static final String TYPE_EPISODE = "episode";
    }


    public static final class SearchOMDB {

        public static final String KEY_TITLE = "Title";
        public static final String KEY_YEAR = "Year";
        public static final String KEY_IMDBID = "imdbID";
        public static final String KEY_TYPE = "Type";
        public static final String KEY_POSTER = "Poster";

        public static final String KEY_RESPONSE = "Response";
        public static final String KEY_TOTALRESULTS = "totalResults";
        public static final String KEY_SEARCH = "Search";

        public static final String RESPONSE_TRUE = "True";
        public static final String RESPONSE_FALSE = "False";

        public static final String TYPE_MOVIE = "movie";
        public static final String TYPE_SERIES = "series";
        public static final String TYPE_EPISODE = "episode";
    }

}
