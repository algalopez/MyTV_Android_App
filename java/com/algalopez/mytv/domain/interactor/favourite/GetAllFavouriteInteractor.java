package com.algalopez.mytv.domain.interactor.favourite;


import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.FavouriteEntity;
import com.algalopez.mytv.domain.model.ShowEntity;
import com.algalopez.mytv.domain.repository.ILocalRepository;

import java.util.ArrayList;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/2/16
 */

public class GetAllFavouriteInteractor extends CallbackInteractor<ResponseModel<?, ShowEntity>> {




    private ILocalRepository mLocalRepository;

    public GetAllFavouriteInteractor(ILocalRepository localRepository){
        mLocalRepository = localRepository;
    }


    @Override
    public ResponseModel<FavouriteEntity, ShowEntity> run() {

        sendProgress(0,1);

        ArrayList<ShowEntity> favouriteData = mLocalRepository.getAllFavourite();

        FavouriteEntity favouriteHeader = new FavouriteEntity();

        ResponseModel<FavouriteEntity, ShowEntity> response = new ResponseModel<>();
        response.setAction(ResponseModel.ActionType.FAVOURITE);
        response.setRepository(ResponseModel.RepositoryType.LOCAL);

        response.setHeader(favouriteHeader);

        for (ShowEntity item : favouriteData){
            response.appendData(item);
        }

        sendSuccess();

        return response;
    }


}
