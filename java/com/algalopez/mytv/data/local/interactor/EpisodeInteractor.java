package com.algalopez.mytv.data.local.interactor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.algalopez.mytv.data.local.database.MyTVDbContract.EpisodeEntry;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */


public class EpisodeInteractor {

    private ContentResolver mContentResolver;

    public EpisodeInteractor(ContentResolver contentResolver){

        this.mContentResolver = contentResolver;
    }


    /**
     * Get all episodes of a show and season
     */
    public class getAllEpisode implements IInteractor<Cursor> {

        String mShowID;
        String mSeason;
        String mSortOrder;

        public getAllEpisode(String showID, String season, String sortOrder){
            this.mShowID = showID;
            this.mSeason = season;
            this.mSortOrder = sortOrder;
        }

        @Override
        public Cursor run() {

            Uri episodeUri = EpisodeEntry.buildEpisode(mShowID, mSeason);
            String[] projection = EpisodeEntry.getEpisodeProjection();
            String selection = EpisodeEntry.COLUMN_SERIESID + " = ? AND " + EpisodeEntry.COLUMN_SEASON + " = ?";
            String[] selectionArgs = new String[]{mShowID, mSeason};

            return mContentResolver.query(episodeUri, projection, selection, selectionArgs, mSortOrder);
        }
    }


    /**
     * Get episode
     */
    public class getEpisode implements IInteractor<Cursor> {

        String mShowID;
        String mSeason;
        String mEpisode;
        String mSortOrder;

        public getEpisode(String showID, String season, String episode, String sortOrder) {
            this.mShowID = showID;
            this.mSeason = season;
            this.mEpisode = episode;
            this.mSortOrder = sortOrder;
        }

        @Override
        public Cursor run() {
            Uri episodeUri = EpisodeEntry.buildEpisode(mShowID, mSeason, mEpisode);
            String[] projection = EpisodeEntry.getEpisodeProjection();
            String selection = EpisodeEntry.COLUMN_SERIESID + " = ? AND " + EpisodeEntry.COLUMN_SEASON + " = ? AND " + EpisodeEntry.COLUMN_EPISODE + " = ?";
            String[] selectionArgs = new String[]{mShowID, mSeason, mEpisode};
            return mContentResolver.query(episodeUri, projection, selection, selectionArgs, mSortOrder);
        }
    }


    /**
     * Add episode
     */
    public class setEpisode implements IInteractor<Uri> {

        String mShowID;
        String mSeason;
        String mEpisode;
        ContentValues mContentValues;

        public setEpisode(String showID, String season, String episode, ContentValues contentValues) {
            this.mShowID = showID;
            this.mSeason = season;
            this.mEpisode = episode;
            this.mContentValues = contentValues;
        }

        @Override
        public Uri run() {

            Uri episodeUri = EpisodeEntry.buildEpisode(mShowID, mSeason, mEpisode);
            return mContentResolver.insert(episodeUri, mContentValues);
        }
    }


    /**
     * Update episode
     */
    public class updateEpisode implements IInteractor<Integer> {

        String mShowID;
        String mSeason;
        String mEpisode;
        ContentValues mContentValues;

        public updateEpisode(String showID, String season, String episode, ContentValues contentValues) {
            this.mShowID = showID;
            this.mSeason = season;
            this.mEpisode = episode;
            this.mContentValues = contentValues;
        }

        @Override
        public Integer run() {
            Uri episodeUri = EpisodeEntry.buildEpisode(mShowID, mSeason, mEpisode);
            String where = EpisodeEntry.COLUMN_SERIESID + " = ? AND " + EpisodeEntry.COLUMN_SEASON + " = ? AND " + EpisodeEntry.COLUMN_EPISODE + " = ?";
            String[] selectionArgs = new String[]{mShowID, mSeason, mEpisode};
            return mContentResolver.update(episodeUri, mContentValues, where, selectionArgs);
        }
    }


    /**
     * Remove episode
     */
    public class removeEpisode implements IInteractor<Integer> {

        String mShowID;
        String mSeason;
        String mEpisode;

        public removeEpisode(String showID, String season, String episode) {
            this.mShowID = showID;
            this.mSeason = season;
            this.mEpisode = episode;
        }

        @Override
        public Integer run() {
            Uri episodeUri = EpisodeEntry.buildEpisode(mShowID, mSeason, mEpisode);
            String where = EpisodeEntry.COLUMN_SERIESID + " = ? AND " + EpisodeEntry.COLUMN_SEASON + " = ? AND " + EpisodeEntry.COLUMN_EPISODE + " = ?";
            String[] selectionArgs = new String[]{mShowID, mSeason, mEpisode};
            return mContentResolver.delete(episodeUri, where, selectionArgs);
        }
    }


}
