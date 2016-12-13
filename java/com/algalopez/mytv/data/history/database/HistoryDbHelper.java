package com.algalopez.mytv.data.history.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */

class HistoryDbHelper extends SQLiteOpenHelper {

    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "history.db";


    HistoryDbHelper(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_SEARCH_TABLE = "CREATE TABLE " + HistoryDbContract.SearchEntry.TABLE_NAME + " ("+
                HistoryDbContract.SearchEntry._ID + " INTEGER PRIMARY KEY," +
                HistoryDbContract.SearchEntry.COLUMN_SEARCHTERM + " TEXT, " +
                HistoryDbContract.SearchEntry.COLUMN_TOTALRESULTS + " TEXT " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_SEARCH_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + HistoryDbContract.SearchEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}

