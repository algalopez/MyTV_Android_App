package com.algalopez.mytv.data.local.interactor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.algalopez.mytv.data.local.database.MyTVDbContract.SeasonEntry;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */




public class SeasonInteractor {

    private ContentResolver mContentResolver;

    public SeasonInteractor(ContentResolver contentResolver){

        this.mContentResolver = contentResolver;
    }


    /**
     * Get all seasons of a show
     */
    public class getAllSeason implements IInteractor<Cursor> {

        String mShowID;
        String mSortOrder;

        public getAllSeason(String showID, String sortOrder){
            this.mShowID = showID;
            this.mSortOrder = sortOrder;
        }

        @Override
        public Cursor run() {
            Uri seasonUri = SeasonEntry.buildSeason(mShowID);
            String[] projection = SeasonEntry.getSeasonProjection();
            String selection = SeasonEntry.COLUMN_SHOWID + " = ?";
            String[] selectionArgs = new String[]{mShowID};
            return mContentResolver.query(seasonUri, projection, selection, selectionArgs, mSortOrder);
        }
    }


    /**
     * Get season
     */
    public class getSeason implements IInteractor<Cursor> {

        String mShowID;
        String mSeason;
        String mSortOrder;

        public getSeason(String showID, String season, String sortOrder) {
            this.mShowID = showID;
            this.mSeason = season;
            this.mSortOrder = sortOrder;
        }

        @Override
        public Cursor run() {
            Uri seasonUri = SeasonEntry.buildSeason(mShowID, mSeason);
            String[] projection = SeasonEntry.getSeasonProjection();
            String selection = SeasonEntry.COLUMN_SHOWID + " = ? AND " + SeasonEntry.COLUMN_SEASON + " = ?";
            String[] selectionArgs = new String[]{mShowID, mSeason};
            return mContentResolver.query(seasonUri, projection, selection, selectionArgs, mSortOrder);
        }
    }


    /**
     * Add season
     */
    public class setSeason implements IInteractor<Uri> {

        String mShowID;
        String mSeason;
        ContentValues mContentValues;

        public setSeason(String showID, String season, ContentValues contentValues) {
            this.mShowID = showID;
            this.mSeason = season;
            this.mContentValues = contentValues;
        }

        @Override
        public Uri run() {

            Uri seasonUri = SeasonEntry.buildSeason(mShowID, mSeason);
            return mContentResolver.insert(seasonUri, mContentValues);
        }
    }


    /**
     * Update season
     */
    public class updateSeason implements IInteractor<Integer> {

        String mShowID;
        String mSeason;
        ContentValues mContentValues;

        public updateSeason(String showID, String season, ContentValues contentValues) {
            this.mShowID = showID;
            this.mSeason = season;
            this.mContentValues = contentValues;
        }

        @Override
        public Integer run() {

            Uri seasonUri = SeasonEntry.buildSeason(mShowID, mSeason);
            String where = SeasonEntry.COLUMN_SHOWID + " = ? AND " + SeasonEntry.COLUMN_SEASON + " = ?";
            String[] selectionArgs = new String[]{mShowID, mSeason};
            return mContentResolver.update(seasonUri, mContentValues, where, selectionArgs);
        }
    }


    /**
     * Remove season
     */
    public class removeSeason implements IInteractor<Integer> {

        String mShowID;
        String mSeason;

        public removeSeason(String showID, String season) {
            this.mShowID = showID;
            this.mSeason = season;
        }

        @Override
        public Integer run() {

            Uri seasonUri = SeasonEntry.buildSeason(mShowID, mSeason);
            String where = SeasonEntry.COLUMN_SHOWID + " = ? AND " + SeasonEntry.COLUMN_SEASON + " = ?";
            String[] selectionArgs = new String[]{mShowID, mSeason};
            return mContentResolver.delete(seasonUri, where, selectionArgs);
        }
    }

}
