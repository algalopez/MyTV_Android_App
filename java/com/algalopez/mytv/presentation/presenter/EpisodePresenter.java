package com.algalopez.mytv.presentation.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.algalopez.mytv.data.local.LocalRepository;
import com.algalopez.mytv.data.omdb.NetworkRepository;
import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.interactor.episode.UpdateEpisodeInteractor;
import com.algalopez.mytv.domain.interactor.show.InsertShowInteractor;
import com.algalopez.mytv.domain.interactor.show.RemoveShowInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.interactor.IInteractor;
import com.algalopez.mytv.domain.interactor.episode.GetEpisodeInteractor;
import com.algalopez.mytv.domain.scheduler.AsyncTaskLoaderLoader;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;
import com.algalopez.mytv.domain.scheduler.IExecutor;
import com.algalopez.mytv.domain.scheduler.ThreadExecutor;
import com.algalopez.mytv.presentation.fragment.IFragment;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/4/16
 */

public class EpisodePresenter implements LoaderManager.LoaderCallbacks<ResponseModel>, CallbackInteractor.Callback {
    private final static String LOGTAG = "EpisodePresenter";


    private static final int EPISODE_LOADER = 4;

    private static final String PARAM_SHOWID = "PARAM_SHOWID";
    private static final String PARAM_SEASON = "PARAM_SEASON";
    private static final String PARAM_EPISODE = "PARAM_EPISODE";

    private String mShowID;
    private String mSeason;
    private String mEpisode;

    private IFragment mView;
    private LoaderManager mLoaderManager;

    private ILocalRepository mlocalRepository;
    private INetworkRepository mNetworkRepository;


    public EpisodePresenter(IFragment episodeView, LoaderManager loaderManager){
        this.mView = episodeView;
        this.mLoaderManager = loaderManager;
    }



    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------



    public void onStart(String showID, String season, String episode){

        this.mShowID = showID;
        this.mSeason = season;
        this.mEpisode = episode;

        mlocalRepository = new LocalRepository(mView.getViewContext().getContentResolver());
        mNetworkRepository = new NetworkRepository();

        if (mLoaderManager.getLoader(EPISODE_LOADER) == null) {
            mLoaderManager.initLoader(EPISODE_LOADER, null, this).forceLoad();
        } else {
            mLoaderManager.restartLoader(EPISODE_LOADER, null, this).forceLoad();
        }
    }


    public void onStop(){

        mView = null;
        mLoaderManager = null;
    }



    public void saveState(Bundle outState){

        outState.putString(PARAM_SHOWID, mShowID);
        outState.putString(PARAM_SEASON, mSeason);
        outState.putString(PARAM_EPISODE, mEpisode);
    }

    public void restoreState(Bundle inState){

        mShowID = inState.getString(PARAM_SHOWID);
        mSeason = inState.getString(PARAM_SEASON);
        mEpisode = inState.getString(PARAM_EPISODE);
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


    public void refreshEpisode(){
        //Log.d(LOGTAG, "refreshShow");

        IExecutor<CallbackInteractor> executor = ThreadExecutor.getInstance();

        CallbackInteractor interactor = new UpdateEpisodeInteractor(mShowID, mSeason, mEpisode, mlocalRepository, mNetworkRepository);
        interactor.setCallback(this);

        executor.execute(interactor);
    }



    // ---------------------------------------------------------------------------------------------
    // PROGRESS CALLBACKS
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onProgressReceived(int actual, int total) {
        //Log.d("AAA ", actual + " / " + total);
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

//        Log.d(LOGTAG, "showid: " + mShowID + " - season: " + mSeason + ", episode: " + mEpisode);

        ILocalRepository localRepository = new LocalRepository(mView.getViewContext().getContentResolver());
        INetworkRepository networkRepository = new NetworkRepository();

        IInteractor interactor = new GetEpisodeInteractor(mShowID, mSeason, mEpisode, localRepository, networkRepository);

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
