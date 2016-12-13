package com.algalopez.mytv.domain.interactor.episode;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/2/16
 */

public class GetEpisodeInteractor extends CallbackInteractor<ResponseModel<EpisodeEntity, ?>> {

    private String mShowID;
    private String mSeason;
    private String mEpisode;
    private ILocalRepository mLocalRepository;
    private INetworkRepository mNetworkRepository;

    public GetEpisodeInteractor(String showID, String season, String episode, ILocalRepository localRepository, INetworkRepository networkRepository){
        this.mShowID = showID;
        this.mSeason = season;
        this.mEpisode = episode;
        this.mLocalRepository = localRepository;
        this.mNetworkRepository = networkRepository;
    }



    @Override
    public ResponseModel<EpisodeEntity, ?> run() {

        sendProgress(0,1);

        ResponseModel<EpisodeEntity, ?> response = new ResponseModel<>();

        EpisodeEntity header = mLocalRepository.getEpisode(mShowID, mSeason, mEpisode);

        if (header.getAction().equals(AEntity.ActionType.ERROR)){
            response = mNetworkRepository.getEpisode(mShowID, mSeason, mEpisode);
            response.setRepository(ResponseModel.RepositoryType.NETWORK);
            return response;
        }

        response.setAction(ResponseModel.ActionType.EPISODE);
        response.setRepository(ResponseModel.RepositoryType.LOCAL);
        response.setHeader(header);

        sendSuccess();

        return response;
    }
}
