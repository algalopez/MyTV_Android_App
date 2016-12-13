package com.algalopez.mytv.domain.interactor.search;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.domain.model.ShowEntity;
import com.algalopez.mytv.domain.repository.INetworkRepository;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/2/16
 */


public class SearchTermInteractor extends CallbackInteractor<ResponseModel<SearchEntity, ShowEntity>> {

    private String mSearchTerm;
    private INetworkRepository mNetworkRepository;


    public SearchTermInteractor(String searchTerm, INetworkRepository networkRepository){
        this.mSearchTerm = searchTerm;
        this.mNetworkRepository = networkRepository;
    }




    @Override
    public ResponseModel<SearchEntity, ShowEntity> run() {

        sendProgress(0,1);

        ResponseModel<SearchEntity, ShowEntity> response = mNetworkRepository.search(mSearchTerm);
        response.setAction(ResponseModel.ActionType.SEARCH);
        response.setRepository(ResponseModel.RepositoryType.NETWORK);

        sendSuccess();
        return response;

    }




}
