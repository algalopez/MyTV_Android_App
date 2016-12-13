package com.algalopez.mytv.domain.interactor.show;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.repository.ILocalRepository;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/2/16
 */

public class RemoveShowInteractor extends CallbackInteractor<Integer> {

    private ILocalRepository mLocalRepository;
    private String mShowID;
    public RemoveShowInteractor(String showID, ILocalRepository localRepository){
        this.mShowID = showID;
        this.mLocalRepository = localRepository;
    }




    @Override
    public Integer run() {

        sendProgress(0,1);

        int totalRemoved = 0;

        ArrayList<SeasonEntity> seasons = mLocalRepository.getSeason(mShowID);
        for (SeasonEntity seasonItem: seasons){
            ArrayList<EpisodeEntity> episodes = mLocalRepository.getEpisode(mShowID, seasonItem.getSeason());
            for (EpisodeEntity episodeItem: episodes){
                totalRemoved += mLocalRepository.removeEpisode(mShowID, seasonItem.getSeason(), episodeItem.getEpisode());
            }
            totalRemoved += mLocalRepository.removeSeason(mShowID, seasonItem.getSeason());
        }
        totalRemoved += mLocalRepository.removeShow(mShowID);

        sendSuccess();
        return totalRemoved;
    }
}
