package com.algalopez.mytv.domain.interactor.history;


import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.domain.repository.IHistoryRepository;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/9/16
 */

public class SetHistoryInteractor extends CallbackInteractor<Long> {

    private IHistoryRepository historyRepository;
    private SearchEntity mData;

    public SetHistoryInteractor(SearchEntity data, IHistoryRepository repository){

        this.historyRepository = repository;
        this.mData = data;
    }



    @Override
    public Long run() {

        sendProgress(0,1);

        // Check if already exists
        ArrayList<SearchEntity> history = historyRepository.getHistory();
        boolean already_exist = false;
        for (SearchEntity historyItem: history){
            if (historyItem.getSearchTerm().equals(mData.getSearchTerm())){
                already_exist = true;
            }
        }

        if (already_exist){
            sendError();
            return 0L;
        } else {
            Long set = historyRepository.setHistory(mData);
            sendSuccess();
            return set;
        }


    }
}
