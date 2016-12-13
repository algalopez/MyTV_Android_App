package com.algalopez.mytv.data.local.interactor;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import com.algalopez.mytv.data.local.database.MyTVDbContract;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */



public class FavouriteInteractor {

    private ContentResolver mContentResolver;


    public FavouriteInteractor(ContentResolver contentResolver){

        this.mContentResolver = contentResolver;
    }


    /**
     * Get all favourite shows
     */
    public class getAllFavouriteInteractor implements IInteractor<Cursor>{
        String mSortOrder;
        public getAllFavouriteInteractor(String sortOrder) {
            this.mSortOrder = sortOrder;
        }

        @Override
        public Cursor run() {
            Uri favouriteUri = MyTVDbContract.ShowEntry.buildFavourite();
            String[] projection = MyTVDbContract.ShowEntry.getFavouriteProjection();
            return mContentResolver.query(favouriteUri, projection, null, null, mSortOrder);
        }
    }


    /**
     * Remove all favourite shows, including seasons and episodes
     */
    public class removeAllFavouriteInteractor implements IInteractor<Integer>{

        public removeAllFavouriteInteractor() {
        }
        @Override
        public Integer run() {

            int total = 0;

            Uri showUri = MyTVDbContract.ShowEntry.buildShow("a");
            Uri seasonUri = MyTVDbContract.SeasonEntry.buildSeason("a", "1");
            Uri episodeUri = MyTVDbContract.EpisodeEntry.buildEpisode("a", "1", "1");

            total += mContentResolver.delete(showUri, null, null);
            total += mContentResolver.delete(seasonUri, null, null);
            total += mContentResolver.delete(episodeUri, null, null);

            return total;
        }
    }

}
