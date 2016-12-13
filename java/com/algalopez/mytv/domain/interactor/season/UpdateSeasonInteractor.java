package com.algalopez.mytv.domain.interactor.season;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    12/13/16
 */

public class UpdateSeasonInteractor extends CallbackInteractor<Integer> {

    private String mShowID;
    private String mSeason;
    private ILocalRepository mLocalRepository;
    private INetworkRepository mNetworkRepository;

    public UpdateSeasonInteractor(String showID, String season, ILocalRepository localRepository, INetworkRepository networkRepository){
        this.mShowID = showID;
        this.mSeason = season;
        this.mLocalRepository = localRepository;
        this.mNetworkRepository = networkRepository;
    }

    @Override
    public Integer run() {

        int actual = 0;
        int total = 1;

        sendProgress(actual,total);

        int totalUpdated = 0;
        int has_updated;

        // Download season
        ResponseModel<SeasonEntity, EpisodeEntity> seasonResponse = mNetworkRepository.getSeason(mShowID, mSeason);

        // Store or update show on local repository
        has_updated = mLocalRepository.updateSeason(mShowID, mSeason, seasonResponse.getHeader());
        if (has_updated == 0) {
            mLocalRepository.setSeason(mShowID, mSeason, seasonResponse.getHeader());
        }
        totalUpdated += 1;

        total += seasonResponse.getData().size();
        sendProgress(++actual, total);

        // For every episode in the season
        for (EpisodeEntity episodeItem: seasonResponse.getData()){

            // Download the episode
            ResponseModel<EpisodeEntity, ?> episodeResponse = mNetworkRepository.getEpisode(mShowID, mSeason, episodeItem.getEpisode());

            // Store or update the episode on local repository
            has_updated = mLocalRepository.updateEpisode(mShowID, mSeason, episodeItem.getEpisode(), episodeResponse.getHeader());
            if (has_updated == 0) {
                mLocalRepository.setEpisode(mShowID, mSeason, episodeItem.getEpisode(), episodeResponse.getHeader());
            }
            totalUpdated += 1;

            sendProgress(++actual, total);
        }

        sendSuccess();
        return totalUpdated;
    }
}
