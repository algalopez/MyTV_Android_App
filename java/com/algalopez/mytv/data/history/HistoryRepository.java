package com.algalopez.mytv.data.history;

import android.content.Context;

import com.algalopez.mytv.data.history.converter.HistoryConverter;
import com.algalopez.mytv.data.history.database.HistoryDbDAO;
import com.algalopez.mytv.data.history.interactor.HistoryInteractor;
import com.algalopez.mytv.data.history.interactor.IInteractor;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.domain.repository.IHistoryRepository;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */

public class HistoryRepository implements IHistoryRepository{

    private HistoryDbDAO mDAO;

    public HistoryRepository(Context context){
        mDAO = new HistoryDbDAO(context);
    }

    public HistoryRepository(HistoryDbDAO historyDbDAO){
        this.mDAO = historyDbDAO;
    }

    /**
     * Get an ArrayList of the performed searches
     * Convert searches from history model to domain model
     */
    @Override
    public ArrayList<SearchEntity> getHistory() {

        IInteractor<ArrayList<com.algalopez.mytv.data.history.model.HistoryEntity>> interactor = new HistoryInteractor(mDAO).new GetHistoryInteractor();
        HistoryConverter converter = new HistoryConverter();

        return converter.convertAllToDomainModel(interactor.run());
    }


    /**
     * Convert search from domain model to history model
     * Add search to the database
     */
    @Override
    public long setHistory(SearchEntity data) {
        HistoryConverter converter = new HistoryConverter();

        IInteractor<Long> interactor = new HistoryInteractor(mDAO).new SetHistoryInteractor(converter.convertToDataModel(data));
        return interactor.run();
    }


    /**
     * Remove all the searches
     */
    @Override
    public long removeHistory() {

        IInteractor<Long> interactor = new HistoryInteractor(mDAO).new RemoveHistoryInteractor();
        return interactor.run();
    }
}
