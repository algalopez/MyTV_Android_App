package com.algalopez.mytv.data.local.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.algalopez.mytv.data.local.database.MyTVDbContract;
import com.algalopez.mytv.data.local.database.MyTVDbHelper;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    9/14/16
 */

public class MyTVProvider extends ContentProvider {

    private final static String LOGTAG = "MyTVProvider";

    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MyTVDbHelper mOpenHelper;


    final static int FAVOURITE = 1;
    final static int SHOW = 2;
    final static int SEASON_LIST = 3;
    final static int SEASON = 4;
    final static int EPISODE_LIST = 5;
    final static int EPISODE = 6;


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MyTVDbContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MyTVDbContract.PATH_FAVOURITE, FAVOURITE);
        matcher.addURI(authority, MyTVDbContract.PATH_SHOW, FAVOURITE);
        matcher.addURI(authority, MyTVDbContract.PATH_SHOW + "/*", SHOW);
        matcher.addURI(authority, MyTVDbContract.PATH_SHOW + "/*/" + MyTVDbContract.PATH_SEASON, SEASON_LIST);
        matcher.addURI(authority, MyTVDbContract.PATH_SHOW + "/*/" + MyTVDbContract.PATH_SEASON + "/*", SEASON);
        matcher.addURI(authority, MyTVDbContract.PATH_SHOW + "/*/" + MyTVDbContract.PATH_SEASON + "/*/" + MyTVDbContract.PATH_EPISODE, EPISODE_LIST);
        matcher.addURI(authority, MyTVDbContract.PATH_SHOW + "/*/" + MyTVDbContract.PATH_SEASON + "/*/" + MyTVDbContract.PATH_EPISODE + "/*", EPISODE);

        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new MyTVDbHelper(getContext());
        return true;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {

            case FAVOURITE:
                return MyTVDbContract.ShowEntry.CONTENT_TYPE;
            case SHOW:
                return MyTVDbContract.ShowEntry.CONTENT_ITEM_TYPE;
            case SEASON_LIST:
                return MyTVDbContract.SeasonEntry.CONTENT_TYPE;
            case SEASON:
                return MyTVDbContract.SeasonEntry.CONTENT_ITEM_TYPE;
            case EPISODE_LIST:
                return MyTVDbContract.EpisodeEntry.CONTENT_TYPE;
            case EPISODE:
                return MyTVDbContract.EpisodeEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }


    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case FAVOURITE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MyTVDbContract.ShowEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            } //
            case SHOW: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MyTVDbContract.ShowEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case SEASON_LIST: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MyTVDbContract.SeasonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case SEASON: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MyTVDbContract.SeasonEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case EPISODE_LIST: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MyTVDbContract.EpisodeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case EPISODE: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        MyTVDbContract.EpisodeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return retCursor;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        Uri returnUri;

        String showName;
        String seasonName;

        final int match = sUriMatcher.match(uri);

        switch (match) {
            case SHOW: {
                long _id = db.insert(MyTVDbContract.ShowEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    //showName = MyTVDbContract.ShowEntry.getShowFromUri(uri);
                    returnUri = MyTVDbContract.ShowEntry.buildFavourite();
                }
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case SEASON: {
                long _id = db.insert(MyTVDbContract.SeasonEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    showName = MyTVDbContract.SeasonEntry.getShowFromUri(uri);
                    //String seasonName = MyTVDbContract.SeasonEntry.getSeasonFromUri(uri);
                    returnUri = MyTVDbContract.SeasonEntry.buildSeason(showName);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case EPISODE: {
                long _id = db.insert(MyTVDbContract.EpisodeEntry.TABLE_NAME, null, contentValues);
                if (_id > 0) {
                    showName = MyTVDbContract.EpisodeEntry.getShowFromUri(uri);
                    seasonName = MyTVDbContract.EpisodeEntry.getSeasonFromUri(uri);
                    returnUri = MyTVDbContract.EpisodeEntry.buildEpisode(showName, seasonName);
                } else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        return returnUri;
    }


    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        Uri returnUri;

        String showName;
        String seasonName;

        switch (match) {
            case SHOW:
                rowsDeleted = db.delete(MyTVDbContract.ShowEntry.TABLE_NAME, selection, selectionArgs);
                returnUri = MyTVDbContract.ShowEntry.buildFavourite();
                break;
            case SEASON:
                rowsDeleted = db.delete(MyTVDbContract.SeasonEntry.TABLE_NAME, selection, selectionArgs);
                showName = MyTVDbContract.SeasonEntry.getShowFromUri(uri);
                returnUri = MyTVDbContract.SeasonEntry.buildSeason(showName);
                break;
            case EPISODE:
                rowsDeleted = db.delete(MyTVDbContract.EpisodeEntry.TABLE_NAME, selection, selectionArgs);
                showName = MyTVDbContract.EpisodeEntry.getShowFromUri(uri);
                seasonName = MyTVDbContract.EpisodeEntry.getSeasonFromUri(uri);
                returnUri = MyTVDbContract.EpisodeEntry.buildEpisode(showName, seasonName);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            Log.d(LOGTAG, "Deleted " + String.valueOf(rowsDeleted) + " rows");
            //getContext().getContentResolver().notifyChange(returnUri, null);
        }

        return rowsDeleted;
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String whereClause, String[] whereArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;

        Uri returnUri;
        String showName;
        String seasonName;

        switch (match) {
            case SHOW:
                rowsUpdated = db.update(MyTVDbContract.ShowEntry.TABLE_NAME, contentValues, whereClause, whereArgs);
                returnUri = MyTVDbContract.ShowEntry.buildFavourite();
                break;
            case SEASON:
                rowsUpdated = db.update(MyTVDbContract.SeasonEntry.TABLE_NAME, contentValues, whereClause, whereArgs);
                showName = MyTVDbContract.SeasonEntry.getShowFromUri(uri);
                returnUri = MyTVDbContract.SeasonEntry.buildSeason(showName);
                break;
            case EPISODE:
                rowsUpdated = db.update(MyTVDbContract.EpisodeEntry.TABLE_NAME, contentValues, whereClause, whereArgs);
                showName = MyTVDbContract.EpisodeEntry.getShowFromUri(uri);
                seasonName = MyTVDbContract.EpisodeEntry.getSeasonFromUri(uri);
                returnUri = MyTVDbContract.EpisodeEntry.buildEpisode(showName, seasonName);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        // Because a null deletes all rows
        if (rowsUpdated != 0) {
            Log.d(LOGTAG, "Updated " + String.valueOf(rowsUpdated) + " rows");
            //getContext().getContentResolver().notifyChange(returnUri, null);
        }

        return rowsUpdated;
    }


}
