package com.algalopez.mytv.data.history.database;

import android.provider.BaseColumns;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */

public class HistoryDbContract {


    public static final class SearchEntry implements BaseColumns {

        static final String TABLE_NAME = "history";

        public static final String COLUMN_SEARCHTERM = "SearchTerm";
        public static final String COLUMN_TOTALRESULTS = "TotalResults";


        public static String[] getHistoryProjection() {
            return new String[]{_ID, COLUMN_SEARCHTERM, COLUMN_TOTALRESULTS};
        }
    }



}
