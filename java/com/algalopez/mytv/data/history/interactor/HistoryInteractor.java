package com.algalopez.mytv.data.history.interactor;

import android.content.ContentValues;
import android.database.Cursor;

import com.algalopez.mytv.data.history.database.HistoryDbContract;
import com.algalopez.mytv.data.history.database.HistoryDbDAO;
import com.algalopez.mytv.data.history.model.HistoryEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */

public class HistoryInteractor {

    private HistoryDbDAO mHistoryDAO;

    public HistoryInteractor(HistoryDbDAO historyDAO){

        this.mHistoryDAO = historyDAO;
    }


    /**
     * Get an ArrayList of the performed searches
     */
    public class GetHistoryInteractor implements IInteractor<ArrayList<HistoryEntity>>{

        @Override
        public ArrayList<HistoryEntity> run() {

            // Get searches from the database
            ArrayList<HistoryEntity> response = new ArrayList<>();
            Cursor data = mHistoryDAO.getHistory(HistoryDbContract.SearchEntry.getHistoryProjection(),
                    null, null, null, null);

            // Convert them to an Arraylist of history entities
            if (data.moveToFirst()) {
                do {

                    HistoryEntity item = new HistoryEntity();
                    item.setSearchTerm(data.getString(data.getColumnIndex(HistoryDbContract.SearchEntry.COLUMN_SEARCHTERM)));
                    item.setTotalResults(data.getString(data.getColumnIndex(HistoryDbContract.SearchEntry.COLUMN_TOTALRESULTS)));
                    response.add(item);

                } while (data.moveToNext());
            }

            data.close();

            return response;
        }
    }


    /**
     * Add a new search to the database
     */
    public class SetHistoryInteractor implements IInteractor<Long>{

        HistoryEntity mData;
        public SetHistoryInteractor(HistoryEntity data){
            this.mData = data;
        }

        @Override
        public Long run() {

            ContentValues contentValues = new ContentValues();
            contentValues.put(HistoryDbContract.SearchEntry.COLUMN_SEARCHTERM, mData.getSearchTerm());
            contentValues.put(HistoryDbContract.SearchEntry.COLUMN_TOTALRESULTS, mData.getTotalResults());

            return mHistoryDAO.setHistory(contentValues);
        }
    }


    /**
     * Remove all the searches
     */
    public class RemoveHistoryInteractor implements IInteractor<Long>{

        @Override
        public Long run() {

            return mHistoryDAO.removeHistory(null, null);
        }
    }
}
