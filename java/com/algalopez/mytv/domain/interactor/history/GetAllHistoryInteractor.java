package com.algalopez.mytv.domain.interactor.history;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.domain.repository.IHistoryRepository;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/9/16
 */

public class GetAllHistoryInteractor extends CallbackInteractor<ResponseModel<?, SearchEntity>> {

    private IHistoryRepository historyRepository;

    public GetAllHistoryInteractor(IHistoryRepository repository){
        this.historyRepository = repository;
    }

    @Override
    public ResponseModel<?, SearchEntity> run() {

        sendProgress(0,1);

        ResponseModel<?, SearchEntity> response = new ResponseModel<>();
        response.setAction(ResponseModel.ActionType.HISTORY);
        response.setRepository(ResponseModel.RepositoryType.HISTORY);

        ArrayList<SearchEntity> historyData = historyRepository.getHistory();

        for (SearchEntity historyDataItem: historyData){
            response.appendData(historyDataItem);
        }

        sendSuccess();
        return response;
    }
}
