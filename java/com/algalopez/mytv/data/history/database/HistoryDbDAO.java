package com.algalopez.mytv.data.history.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */

public class HistoryDbDAO {

    protected SQLiteDatabase database;
    private HistoryDbHelper dbHelper;


    public HistoryDbDAO(Context context){

        dbHelper = new HistoryDbHelper(context);
    }


    /**
     * Get History Cursor
     */
    public Cursor getHistory(String[] projection, String selection, String[] selectionArgs, String orderBy, String limit){

        database = dbHelper.getReadableDatabase();
        return database.query(HistoryDbContract.SearchEntry.TABLE_NAME,
                projection,
                selection,      // String   Selection
                selectionArgs,  // String[] SelectionArgs
                null,           // String   groupBy
                null,           // String   having
                orderBy,        // String   orderBy
                limit);         // String limit
    }


    /**
     * Add new search
     */
    public long setHistory(ContentValues data){

        database = dbHelper.getWritableDatabase();
        return database.insert(HistoryDbContract.SearchEntry.TABLE_NAME,
                null,
                data);
    }


    /**
     * Remove all searches
     */
    public long removeHistory(String whereClause, String[] whereArgs){

        database = dbHelper.getWritableDatabase();
        return database.delete(HistoryDbContract.SearchEntry.TABLE_NAME,
                whereClause,
                whereArgs);
    }

}
