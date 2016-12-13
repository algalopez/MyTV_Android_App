package com.algalopez.mytv.domain.interactor.show;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.model.ShowEntity;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/2/16
 */

public class UpdateShowInteractor extends CallbackInteractor<Integer> {

    private String mShowID;
    private ILocalRepository mLocalRepository;
    private INetworkRepository mNetworkRepository;


    public UpdateShowInteractor(String showID, ILocalRepository localRepository, INetworkRepository networkRepository){
        this.mShowID = showID;
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

        // Download show
        ResponseModel<ShowEntity, SeasonEntity> showResponse = mNetworkRepository.getShow(mShowID);

        // Store or update show on local repository
        has_updated = mLocalRepository.updateShow(mShowID, showResponse.getHeader());
        if (has_updated == 0) {
            mLocalRepository.setShow(mShowID, showResponse.getHeader());
        }
        totalUpdated += 1;

        total += showResponse.getData().size();
        sendProgress(++actual, total);

        // For every season in the show
        for (SeasonEntity seasonItem: showResponse.getData()){

            // Download season
            ResponseModel<SeasonEntity, EpisodeEntity> seasonResponse = mNetworkRepository.getSeason(mShowID, seasonItem.getSeason());

            // Store or update the season on local repository
            has_updated = mLocalRepository.updateSeason(mShowID, seasonItem.getSeason(), seasonResponse.getHeader());
            if (has_updated == 0) {
                mLocalRepository.setSeason(mShowID, seasonItem.getSeason(), seasonResponse.getHeader());
            }
            totalUpdated += 1;

            total += seasonResponse.getData().size();
            sendProgress(++actual, total);

            // For every episode in the season
            for (EpisodeEntity episodeItem: seasonResponse.getData()){

                // Download the episode
                ResponseModel<EpisodeEntity, ?> episodeResponse = mNetworkRepository.getEpisode(mShowID, seasonItem.getSeason(), episodeItem.getEpisode());

                // Store or update the episode on local repository
                has_updated = mLocalRepository.updateEpisode(mShowID, seasonItem.getSeason(), episodeItem.getEpisode(), episodeResponse.getHeader());
                if (has_updated == 0) {
                    mLocalRepository.setEpisode(mShowID, seasonItem.getSeason(), episodeItem.getEpisode(), episodeResponse.getHeader());
                }
                totalUpdated += 1;

                sendProgress(++actual, total);
            }
        }

        sendSuccess();
        return totalUpdated;
    }
}
