package com.algalopez.mytv.data.omdb.model;

import android.graphics.Bitmap;

import java.util.ArrayList;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */


public class ShowEntity  extends AEntity<ShowEntity.Header, ShowEntity.Data>{


    public ShowEntity() {
        setType(TYPE_SHOW);
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
        private String year;
        private String rated;
        private String released;
        private String runtime;
        private String genre;
        private String director;
        private String writer;
        private String actors;
        private String plot;
        private String language;
        private String country;
        private String awards;
        private String poster;
        private String metascore;
        private String imdbRating;
        private String imdbVotes;
        private String imdbID;
        private String type;
        private String totalSeasons;
        private Bitmap image;


        public String getTitle() { return title; }
        public String getYear() { return year; }
        public String getRated() { return rated; }
        public String getReleased() { return released; }
        public String getRuntime() { return runtime; }
        public String getGenre() { return genre; }
        public String getDirector() { return director; }
        public String getWriter() { return writer; }
        public String getActors() { return actors; }
        public String getPlot() { return plot; }
        public String getLanguage() { return language; }
        public String getCountry() { return country; }
        public String getAwards() { return awards; }
        public String getPoster() { return poster; }
        public String getMetascore() { return metascore; }
        public String getImdbRating() { return imdbRating; }
        public String getImdbVotes() { return imdbVotes; }
        public String getImdbID() { return imdbID; }
        public String getType() { return type; }
        public String getTotalSeasons() { return totalSeasons; }
        public Bitmap getImage() { return image; }

        public void setTitle(String title) { this.title = title; }
        public void setYear(String year) { this.year = year; }
        public void setRated(String rated) { this.rated = rated; }
        public void setReleased(String released) { this.released = released; }
        public void setRuntime(String runtime) { this.runtime = runtime; }
        public void setGenre(String genre) { this.genre = genre; }
        public void setDirector(String director) { this.director = director; }
        public void setWriter(String writer) { this.writer = writer; }
        public void setActors(String actors) { this.actors = actors; }
        public void setPlot(String plot) { this.plot = plot; }
        public void setLanguage(String language) { this.language = language; }
        public void setCountry(String country) { this.country = country; }
        public void setAwards(String awards) { this.awards = awards; }
        public void setPoster(String poster) { this.poster = poster; }
        public void setMetascore(String metascore) { this.metascore = metascore; }
        public void setImdbRating(String imdbRating) { this.imdbRating = imdbRating; }
        public void setImdbVotes(String imdbVotes) { this.imdbVotes = imdbVotes; }
        public void setImdbID(String imdbID) { this.imdbID = imdbID; }
        public void setType(String type) { this.type = type; }
        public void setTotalSeasons(String totalSeasons) { this.totalSeasons = totalSeasons; }
        public void setImage(Bitmap image) { this.image = image; }

    }


    /*
     * INNER DATA CLASS
     */


    public class Data{
        private String showID;
        private String season;

        public String getShowID() { return showID; }
        public String getSeason() { return season; }

        public void setShowID(String showID) { this.showID = showID; }
        public void setSeason(String season) { this.season = season; }

    }

}
