package com.algalopez.mytv.data.omdb.model;

import android.graphics.Bitmap;

import java.util.ArrayList;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */


public class SearchEntity extends AEntity<SearchEntity.Header, SearchEntity.Data>{

    public SearchEntity() {
        setType(TYPE_SEARCH);
        mHeader = null;
        mData = new ArrayList<Data>();
    }


    @Override public Header newHeader(){ return new Header(); }
    @Override public Data newData(){ return new Data(); }


    /*
     * INNER HEADER CLASS
     */


    public class Header {
        private String searchTerm;
        private String totalResults;
        private String page;

        public String getSearchTerm() { return searchTerm; }
        public String getTotalResults() { return totalResults; }
        public String getPage() { return page; }

        public void setSearchTerm(String searchTerm) { this.searchTerm = searchTerm; }
        public void setTotalResults(String totalResults) { this.totalResults = totalResults; }
        public void setPage(String page) { this.page = page; }
    }


    /*
     * INNER DATA CLASS
     */


    public class Data {
        private String title;
        private String year;
        private String imdbid;
        private String type;
        private String poster;
        private Bitmap image;

        public String getTitle() { return title; }
        public String getYear() { return year; }
        public String getImdbID() { return imdbid; }
        public String getType() { return type; }
        public String getPoster() { return poster; }
        public Bitmap getImage() { return image; }

        public void setTitle(String title) { this.title = title; }
        public void setYear(String year) { this.year = year; }
        public void setImdbid(String imdbid) { this.imdbid = imdbid; }
        public void setType(String type) { this.type = type; }
        public void setPoster(String poster) { this.poster = poster; }
        public void setImage(Bitmap image) { this.image = image; }
    }

}
