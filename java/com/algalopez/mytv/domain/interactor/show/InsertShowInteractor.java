package com.algalopez.mytv.domain.interactor.show;

import android.net.Uri;

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



public class InsertShowInteractor extends CallbackInteractor<Uri> {

    private ILocalRepository mLocal;
    private INetworkRepository mNetwork;

    private String mShowID;
    public InsertShowInteractor(String showID, ILocalRepository local, INetworkRepository network){
        this.mShowID = showID;
        this.mLocal = local;
        this.mNetwork = network;
    }




    // O(n*n)
    @Override
    public Uri run() {

        int progress = 0;
        int total = 1;

        sendProgress(progress,total);

        // Download show
        ResponseModel<ShowEntity, SeasonEntity> showResponse = mNetwork.getShow(mShowID);

        // Store show on local repository
        Uri uri = mLocal.setShow(mShowID, showResponse.getHeader());

        // Send progress
        total += showResponse.getData().size();
        sendProgress(++progress, total);

        // For every season in the show
        for (SeasonEntity seasonItem: showResponse.getData()){

            // Download season
            ResponseModel<SeasonEntity, EpisodeEntity> seasonResponse = mNetwork.getSeason(mShowID, seasonItem.getSeason());

            // Store the season on local repository
            mLocal.setSeason(mShowID, seasonItem.getSeason(), seasonResponse.getHeader());

            // Send progress
            total += seasonResponse.getData().size();
            sendProgress(++progress, total);

            // For every episode in the season
            for (EpisodeEntity episodeItem: seasonResponse.getData()){

                // Download the episode
                ResponseModel<EpisodeEntity, ?> episodeResponse = mNetwork.getEpisode(mShowID, seasonItem.getSeason(), episodeItem.getEpisode());

                // Store the episode on local repository
                mLocal.setEpisode(mShowID, seasonItem.getSeason(), episodeItem.getEpisode(), episodeResponse.getHeader());

                // Send progress
                sendProgress(++progress, total);
            }
        }

        sendSuccess();
        return uri;
    }



}
