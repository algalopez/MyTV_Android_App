package com.algalopez.mytv.presentation.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.algalopez.mytv.data.local.LocalRepository;
import com.algalopez.mytv.data.omdb.NetworkRepository;
import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.interactor.season.UpdateSeasonInteractor;
import com.algalopez.mytv.domain.interactor.show.InsertShowInteractor;
import com.algalopez.mytv.domain.interactor.show.RemoveShowInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.interactor.IInteractor;
import com.algalopez.mytv.domain.interactor.season.GetSeasonInteractor;
import com.algalopez.mytv.domain.scheduler.AsyncTaskLoaderLoader;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;
import com.algalopez.mytv.domain.scheduler.IExecutor;
import com.algalopez.mytv.domain.scheduler.ThreadExecutor;
import com.algalopez.mytv.presentation.fragment.IFragment;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/1/16
 */

public class SeasonPresenter implements LoaderManager.LoaderCallbacks<ResponseModel>, CallbackInteractor.Callback {

    private final static String LOGTAG = "SeasonPresenter";

    private static final int SEASON_LOADER = 3;

    private static final String PARAM_SHOWID = "PARAM_SHOWID";
    private static final String PARAM_SEASON = "PARAM_SEASON";

    private String mShowID;
    private String mSeason;

    private IFragment mView;
    private LoaderManager mLoaderManager;

    private ILocalRepository mlocalRepository;
    private INetworkRepository mNetworkRepository;


    public SeasonPresenter(IFragment seasonView, LoaderManager loaderManager){
        this.mView = seasonView;
        this.mLoaderManager = loaderManager;
    }



    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------



    public void onStart(String showID, String season){

        this.mShowID = showID;
        this.mSeason = season;

        mlocalRepository = new LocalRepository(mView.getViewContext().getContentResolver());
        mNetworkRepository = new NetworkRepository();

        if (mLoaderManager.getLoader(SEASON_LOADER) == null) {
            mLoaderManager.initLoader(SEASON_LOADER, null, this).forceLoad();
        } else {
            mLoaderManager.restartLoader(SEASON_LOADER, null, this).forceLoad();
        }
    }


    public void onStop(){

        mView = null;
        mLoaderManager = null;
    }


    public void saveState(Bundle outState){
        outState.putString(PARAM_SHOWID, mShowID);
        outState.putString(PARAM_SEASON, mSeason);
    }

    public void restoreState(Bundle inState){
        mShowID = inState.getString(PARAM_SHOWID);
        mSeason = inState.getString(PARAM_SEASON);
    }


    // ---------------------------------------------------------------------------------------------
    // PRESENTER METHODS
    // ---------------------------------------------------------------------------------------------


    public void favouriteShow(){
        //Log.d(LOGTAG, "favouriteShow");

        IExecutor<CallbackInteractor> executor = ThreadExecutor.getInstance();

        CallbackInteractor interactor = new InsertShowInteractor(mShowID, mlocalRepository, mNetworkRepository);
        interactor.setCallback(this);

        executor.execute(interactor);
    }


    public void unFavouriteShow(){
        //Log.d(LOGTAG, "unFavouriteShow");

        IExecutor<CallbackInteractor> executor = ThreadExecutor.getInstance();

        CallbackInteractor interactor = new RemoveShowInteractor(mShowID, mlocalRepository);
        interactor.setCallback(this);

        executor.execute(interactor);
    }


    public void refreshSeason(){
        //Log.d(LOGTAG, "refreshShow");

        IExecutor<CallbackInteractor> executor = ThreadExecutor.getInstance();

        CallbackInteractor interactor = new UpdateSeasonInteractor(mShowID, mSeason, mlocalRepository, mNetworkRepository);
        interactor.setCallback(this);

        executor.execute(interactor);
    }

    // ---------------------------------------------------------------------------------------------
    // PROGRESS CALLBACKS
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onProgressReceived(int actual, int total) {

        mView.showLoading(actual/total);
    }


    @Override
    public void onErrorReceived() {

        mView.showError("");
    }


    @Override
    public void onSuccessReceived() {

        mView.showSuccess();
    }


    // ---------------------------------------------------------------------------------------------
    // LOADER CALLBACKS
    // ---------------------------------------------------------------------------------------------



    @Override
    public Loader<ResponseModel> onCreateLoader(int id, Bundle args) {
        ILocalRepository localRepository = new LocalRepository(mView.getViewContext().getContentResolver());
        INetworkRepository networkRepository = new NetworkRepository();

        IInteractor interactor = new GetSeasonInteractor(mShowID, mSeason, localRepository, networkRepository);

        return new AsyncTaskLoaderLoader(mView.getViewContext(), interactor);
    }

    @Override
    public void onLoadFinished(Loader<ResponseModel> loader, ResponseModel data) {
        mView.showResponse(data);
    }

    @Override
    public void onLoaderReset(Loader<ResponseModel> loader) {

        if (mView!= null) {
            mView.reset();
        }
    }
}
