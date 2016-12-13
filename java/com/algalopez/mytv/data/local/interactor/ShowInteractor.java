package com.algalopez.mytv.data.local.interactor;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.algalopez.mytv.data.local.database.MyTVDbContract.ShowEntry;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */



public class ShowInteractor {

    private ContentResolver mContentResolver;

    public ShowInteractor(ContentResolver contentResolver){
        this.mContentResolver = contentResolver;
    }


    /**
     * Get all shows
     */
    public class getAllShow implements IInteractor<Cursor> {

        String mSortOrder;
        public getAllShow(String sortOrder){
            this.mSortOrder = sortOrder;
        }

        @Override
        public Cursor run() {
            Uri favouriteUri = ShowEntry.buildFavourite();
            String[] projection = ShowEntry.getFavouriteProjection();
            return mContentResolver.query(favouriteUri, projection, null, null, mSortOrder);
        }
    }


    /**
     * Get show
     */
    public class getShow implements IInteractor<Cursor> {

        String mSortOrder;
        String mShowID;

        public getShow(String showID, String sortOrder) {
            this.mShowID = showID;
            this.mSortOrder = sortOrder;
        }

        @Override
        public Cursor run() {
            Uri showUri = ShowEntry.buildShow(mShowID);
            String[] projection = ShowEntry.getFavouriteProjection();
            String selection = ShowEntry.COLUMN_IMDBID + " = ? ";
            String[] selectionArgs = {mShowID};
            return mContentResolver.query(showUri, projection, selection, selectionArgs, mSortOrder);
        }
    }


    /**
     * Add show
     */
    public class setShow implements IInteractor<Uri> {

        String mShowID;
        ContentValues mContentValues;

        public setShow(String showID, ContentValues contentValues) {
            this.mShowID = showID;
            this.mContentValues = contentValues;
        }

        @Override
        public Uri run() {
            Uri showUri = ShowEntry.buildShow(mShowID);
            return mContentResolver.insert(showUri, mContentValues);
        }
    }


    /**
     * Update show
     */
    public class updateShow implements IInteractor<Integer> {

        String mShowID;
        ContentValues mContentValues;

        public updateShow(String showID, ContentValues contentValues) {
            this.mShowID = showID;
            this.mContentValues = contentValues;
        }

        @Override
        public Integer run() {
            Uri showUri = ShowEntry.buildShow(mShowID);
            String where = ShowEntry.COLUMN_IMDBID + " = ?";
            String[] selectionArgs = new String[]{mShowID};
            return mContentResolver.update(showUri, mContentValues, where, selectionArgs);
        }
    }


    /**
     * Remove show
     */
    public class removeShow implements IInteractor<Integer> {

        String mShowID;

        public removeShow(String showID) {
            this.mShowID = showID;
        }

        @Override
        public Integer run() {
            Uri showUri = ShowEntry.buildShow(mShowID);
            String where = ShowEntry.COLUMN_IMDBID + " = ?";
            String[] selectionArgs = new String[]{mShowID};
            return mContentResolver.delete(showUri, where, selectionArgs);
        }
    }

}
