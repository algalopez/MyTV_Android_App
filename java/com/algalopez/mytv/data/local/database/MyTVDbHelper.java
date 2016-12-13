package com.algalopez.mytv.data.local.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.algalopez.mytv.data.local.database.MyTVDbContract.ShowEntry;
import com.algalopez.mytv.data.local.database.MyTVDbContract.SeasonEntry;
import com.algalopez.mytv.data.local.database.MyTVDbContract.EpisodeEntry;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    9/14/16
 */


public class MyTVDbHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 2;
    private final static String DATABASE_NAME = "mytv.db";


    public MyTVDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_SHOW_TABLE = "CREATE TABLE " + ShowEntry.TABLE_NAME + " (" +
                ShowEntry._ID + " INTEGER PRIMARY KEY," +
                ShowEntry.COLUMN_TITLE + " TEXT, " +
                ShowEntry.COLUMN_YEAR + " TEXT, " +
                ShowEntry.COLUMN_RATED + " TEXT, " +
                ShowEntry.COLUMN_RELEASED + " TEXT, " +
                ShowEntry.COLUMN_RUNTIME + " TEXT, " +
                ShowEntry.COLUMN_GENRE + " TEXT, " +
                ShowEntry.COLUMN_DIRECTOR + " TEXT, " +
                ShowEntry.COLUMN_WRITER + " TEXT, " +
                ShowEntry.COLUMN_ACTORS + " TEXT, " +
                ShowEntry.COLUMN_PLOT + " TEXT, " +
                ShowEntry.COLUMN_LANGUAGE + " TEXT, " +
                ShowEntry.COLUMN_COUNTRY + " TEXT, " +
                ShowEntry.COLUMN_AWARDS + " TEXT, " +
                ShowEntry.COLUMN_POSTER + " TEXT, " +
                ShowEntry.COLUMN_METASCORE + " TEXT, " +
                ShowEntry.COLUMN_IMDBRATING + " TEXT, " +
                ShowEntry.COLUMN_IMDBVOTES + " TEXT, " +
                ShowEntry.COLUMN_IMDBID + " TEXT NOT NULL, " +
                ShowEntry.COLUMN_TYPE + " TEXT, " +
                ShowEntry.COLUMN_TOTALSEASONS + " TEXT, " +
                ShowEntry.COLUMN_IMAGE + " BLOB " +
                " );";

        final String SQL_CREATE_SEASON_TABLE = "CREATE TABLE " + SeasonEntry.TABLE_NAME + " (" +
                SeasonEntry._ID + " INTEGER PRIMARY KEY, " +
                SeasonEntry.COLUMN_TITLE + " TEXT, " +
                SeasonEntry.COLUMN_SEASON + " TEXT, " +
                SeasonEntry.COLUMN_TOTALSEASONS + " TEXT, " +
                SeasonEntry.COLUMN_TOTALEPISODES + " TEXT, " +
                SeasonEntry.COLUMN_RELEASEDFIRST + " TEXT, " +
                SeasonEntry.COLUMN_RELEASEDLAST + " TEXT, " +
                SeasonEntry.COLUMN_SHOWID + " TEXT NOT NULL" +
                " );";

        final String SQL_CREATE_EPISODE_TABLE = "CREATE TABLE " + EpisodeEntry.TABLE_NAME + " (" +
                EpisodeEntry._ID + " INTEGER PRIMARY KEY," +
                EpisodeEntry.COLUMN_TITLE + " TEXT, " +
                EpisodeEntry.COLUMN_YEAR + " TEXT, " +
                EpisodeEntry.COLUMN_RATED + " TEXT, " +
                EpisodeEntry.COLUMN_RELEASED + " TEXT, " +
                EpisodeEntry.COLUMN_SEASON + " TEXT, " +
                EpisodeEntry.COLUMN_EPISODE + " TEXT, " +
                EpisodeEntry.COLUMN_RUNTIME + " TEXT, " +
                EpisodeEntry.COLUMN_GENRE + " TEXT, " +
                EpisodeEntry.COLUMN_DIRECTOR + " TEXT, " +
                EpisodeEntry.COLUMN_WRITER + " TEXT, " +
                EpisodeEntry.COLUMN_ACTORS + " TEXT, " +
                EpisodeEntry.COLUMN_PLOT + " TEXT, " +
                EpisodeEntry.COLUMN_LANGUAGE + " TEXT, " +
                EpisodeEntry.COLUMN_COUNTRY + " TEXT, " +
                EpisodeEntry.COLUMN_AWARDS + " TEXT, " +
                EpisodeEntry.COLUMN_POSTER + " TEXT, " +
                EpisodeEntry.COLUMN_METASCORE + " TEXT, " +
                EpisodeEntry.COLUMN_IMDBRATING + " TEXT, " +
                EpisodeEntry.COLUMN_IMDBVOTES + " TEXT, " +
                EpisodeEntry.COLUMN_IMDBID + " TEXT, " +
                EpisodeEntry.COLUMN_SERIESID + " TEXT NOT NULL, " +
                EpisodeEntry.COLUMN_TYPE + " TEXT " +
                " );";


        sqLiteDatabase.execSQL(SQL_CREATE_SHOW_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_SEASON_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_EPISODE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + ShowEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + SeasonEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + EpisodeEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
