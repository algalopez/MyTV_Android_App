package com.algalopez.mytv.domain.interactor.season;


import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/2/16
 */

public class GetSeasonInteractor extends CallbackInteractor<ResponseModel<SeasonEntity, EpisodeEntity>> {

//    private final static String LOGTAG = "GetSeasonInt";


    private String mShowID;
    private String mSeason;
    private ILocalRepository mLocalRepository;
    private INetworkRepository mNetworkRepository;

    public GetSeasonInteractor(String showID, String season, ILocalRepository localRepository, INetworkRepository networkRepository){
        this.mShowID = showID;
        this.mSeason = season;
        this.mLocalRepository = localRepository;
        this.mNetworkRepository = networkRepository;
    }




    @Override
    public ResponseModel<SeasonEntity, EpisodeEntity> run() {

        sendProgress(0,1);

        ResponseModel<SeasonEntity, EpisodeEntity> response = new ResponseModel<>();


        SeasonEntity header = mLocalRepository.getSeason(mShowID, mSeason);
        ArrayList<EpisodeEntity> data = mLocalRepository.getEpisode(mShowID, mSeason);

        if (header.getAction().equals(AEntity.ActionType.ERROR)){
            response = mNetworkRepository.getSeason(mShowID, mSeason);
            response.setRepository(ResponseModel.RepositoryType.NETWORK);
            return response;
        }

        response.setAction(ResponseModel.ActionType.SEASON);
        response.setRepository(ResponseModel.RepositoryType.LOCAL);
        response.setHeader(header);

        for (EpisodeEntity item: data){
            response.appendData(item);
        }

        sendSuccess();
        return response;
    }
}
