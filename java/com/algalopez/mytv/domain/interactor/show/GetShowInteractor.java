package com.algalopez.mytv.domain.interactor.show;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.model.ShowEntity;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/2/16
 */

public class GetShowInteractor extends CallbackInteractor<ResponseModel<ShowEntity, SeasonEntity>> {

    private final static String LOGTAG = "GetShowInt";

    private String mShowID;
    private ILocalRepository mLocalRepository;
    private INetworkRepository mNetworkRepository;

    public GetShowInteractor(String showID, ILocalRepository localRepository, INetworkRepository networkRepository){
        this.mShowID = showID;
        this.mLocalRepository = localRepository;
        this.mNetworkRepository = networkRepository;
    }



    @Override
    public ResponseModel<ShowEntity, SeasonEntity> run() {


        sendProgress(0,1);
        ResponseModel<ShowEntity, SeasonEntity> response = new ResponseModel<>();

        ShowEntity headerResponse = mLocalRepository.getShow(mShowID);
        response.setAction(ResponseModel.ActionType.SHOW);

        // Show not available in local repository. Getting it from network
        if (headerResponse.getAction().equals(AEntity.ActionType.ERROR)){

            response = mNetworkRepository.getShow(mShowID);
            response.setRepository(ResponseModel.RepositoryType.NETWORK);
            return response;
        }

        // Setting the response
        response.setHeader(headerResponse);
        ArrayList<SeasonEntity> dataResponse = mLocalRepository.getSeason(mShowID);
        for (SeasonEntity item: dataResponse){ response.appendData(item); }
        response.setRepository(ResponseModel.RepositoryType.LOCAL);

        sendSuccess();
        return response;
    }
}
