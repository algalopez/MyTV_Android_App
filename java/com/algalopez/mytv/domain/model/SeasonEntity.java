package com.algalopez.mytv.domain.model;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */

public class SeasonEntity extends AEntity {

    public SeasonEntity(){
        super.setAction(ActionType.SEASON);
        totalEpisodes = "0";
        releasedFirst = "";
        releasedLast = "";
    }


    private String title;
    private String season;
    private String totalSeasons;
    private String totalEpisodes;
    private String releasedFirst;
    private String releasedLast;
    private String showID;

    public String getTitle() { return title; }
    public String getSeason() { return season; }
    public String getTotalSeasons() { return totalSeasons; }
    public String getShowID() { return showID; }
    public String getTotalEpisodes() { return totalEpisodes; }
    public String getReleasedFirst() { return releasedFirst; }
    public String getReleasedLast() { return releasedLast; }

    public void setTitle(String title) { this.title = title; }
    public void setSeason(String season) { this.season = season; }
    public void setTotalSeasons(String totalSeasons) { this.totalSeasons = totalSeasons; }
    public void setShowID(String showID) { this.showID = showID; }
    public void setTotalEpisodes(String totalEpisodes) { this.totalEpisodes = totalEpisodes; }
    public void setReleasedFirst(String releasedFirst) { this.releasedFirst = releasedFirst; }
    public void setReleasedLast(String releasedLast) { this.releasedLast = releasedLast; }
}
