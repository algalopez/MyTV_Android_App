package com.algalopez.mytv.domain.interactor.favourite;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.repository.ILocalRepository;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/3/16
 */

public class RemoveAllFavouriteInteractor extends CallbackInteractor<Integer> {

    private ILocalRepository mLocalRepository;

    public RemoveAllFavouriteInteractor(ILocalRepository localRepository){
        mLocalRepository = localRepository;
    }



    @Override
    public Integer run() {

        sendProgress(0,1);

        int removed = mLocalRepository.removeAllFavourite();
        sendSuccess();
        return removed;
    }

}
