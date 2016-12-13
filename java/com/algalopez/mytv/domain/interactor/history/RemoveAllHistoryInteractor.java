package com.algalopez.mytv.domain.interactor.history;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.repository.IHistoryRepository;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/9/16
 */

public class RemoveAllHistoryInteractor extends CallbackInteractor<Long> {

    private IHistoryRepository historyRepository;

    public RemoveAllHistoryInteractor(IHistoryRepository repository){

        this.historyRepository = repository;
    }



    @Override
    public Long run() {

        sendProgress(0,1);
        Long removed = historyRepository.removeHistory();
        sendSuccess();
        return removed;
    }
}
