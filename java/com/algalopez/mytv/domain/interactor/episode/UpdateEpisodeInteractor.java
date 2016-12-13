package com.algalopez.mytv.domain.interactor.episode;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    12/13/16
 */

public class UpdateEpisodeInteractor extends CallbackInteractor<Integer> {

    private String mShowID;
    private String mSeason;
    private String mEpisode;
    private ILocalRepository mLocalRepository;
    private INetworkRepository mNetworkRepository;


    public UpdateEpisodeInteractor(String showID, String season, String episode, ILocalRepository localRepository, INetworkRepository networkRepository){
        this.mShowID = showID;
        this.mSeason = season;
        this.mEpisode = episode;
        this.mLocalRepository = localRepository;
        this.mNetworkRepository = networkRepository;
    }


    @Override
    public Integer run() {

        sendProgress(0,1);

        int totalUpdated = 0;
        int has_updated;

        // Download episode
        ResponseModel<EpisodeEntity, ?> episodeResponse = mNetworkRepository.getEpisode(mShowID, mSeason, mEpisode);

        // Store or update show on local repository
        has_updated = mLocalRepository.updateEpisode(mShowID, mSeason, mEpisode, episodeResponse.getHeader());
        if (has_updated == 0) {
            mLocalRepository.setEpisode(mShowID, mSeason, mEpisode, episodeResponse.getHeader());
        }

        totalUpdated += 1;
        sendProgress(1,1);
        sendSuccess();

        return totalUpdated;
    }
}
