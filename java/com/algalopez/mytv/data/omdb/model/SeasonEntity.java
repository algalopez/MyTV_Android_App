package com.algalopez.mytv.data.omdb.model;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */

public class SeasonEntity  extends AEntity<SeasonEntity.Header, SeasonEntity.Data> {



    public SeasonEntity() {
        setType(TYPE_SEASON);
        mHeader = null;
        mData = new ArrayList<>();
    }

    @Override public Header newHeader(){ return new Header(); }
    @Override public Data newData(){ return new Data(); }


    /*
     * INNER HEADER CLASS
     */


    public class Header{
        private String title;
        private String season;
        private String totalSeasons;
        private String showID;

        public String getTitle() { return title; }
        public String getSeason() { return season; }
        public String getTotalSeasons() { return totalSeasons; }
        public String getShowID() { return showID; }


        public void setTitle(String title) { this.title = title; }
        public void setSeason(String season) { this.season = season; }
        public void setTotalSeasons(String totalSeasons) { this.totalSeasons = totalSeasons; }
        public void setShowID(String showID) { this.showID = showID; }

    }


    /*
     * INNER DATA CLASS
     */


    public class Data{
        private String title;
        private String released;
        private String episode;
        private String imdbRating;
        private String imdbID;
        private String showID;
        private String season;

        public String getTitle() { return title; }
        public String getReleased() { return released; }
        public String getEpisode() { return episode; }
        public String getImdbRating() { return imdbRating; }
        public String getImdbID() { return imdbID; }
        public String getShowID() { return showID; }
        public String getSeason() { return season; }

        public void setTitle(String title) { this.title = title; }
        public void setReleased(String released) { this.released = released; }
        public void setEpisode(String episode) { this.episode = episode; }
        public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }
        public void setImdbID(String imdbID) { this.imdbID = imdbID; }
        public void setShowID(String showID) { this.showID = showID; }
        public void setSeason(String season) { this.season = season; }
    }

}
