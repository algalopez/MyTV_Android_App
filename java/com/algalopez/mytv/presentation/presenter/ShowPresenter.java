package com.algalopez.mytv.presentation.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.algalopez.mytv.data.local.LocalRepository;
import com.algalopez.mytv.data.omdb.NetworkRepository;
import com.algalopez.mytv.domain.interactor.show.InsertShowInteractor;
import com.algalopez.mytv.domain.interactor.show.RemoveShowInteractor;
import com.algalopez.mytv.domain.scheduler.IExecutor;
import com.algalopez.mytv.domain.scheduler.ThreadExecutor;
import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.interactor.show.UpdateShowInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.interactor.IInteractor;
import com.algalopez.mytv.domain.interactor.show.GetShowInteractor;
import com.algalopez.mytv.domain.scheduler.AsyncTaskLoaderLoader;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;
import com.algalopez.mytv.presentation.fragment.IFragment;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/28/16
 */

public class ShowPresenter implements LoaderManager.LoaderCallbacks<ResponseModel>, CallbackInteractor.Callback {

    private final static String LOGTAG = "ShowPresenter";

    public static final int SHOW_LOADER = 2;

    private static final String PARAM_SHOWID = "PARAM_SHOWID";

    private String mShowID;
    private ResponseModel mResponse;

    private IFragment mView;
    private LoaderManager mLoaderManager;

    private ILocalRepository mlocalRepository;
    private INetworkRepository mNetworkRepository;


    public ShowPresenter(IFragment showView, LoaderManager loaderManager){
        this.mView = showView;
        this.mLoaderManager = loaderManager;

    }


    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    public void onStart(String showID){

        this.mShowID = showID;

        mlocalRepository = new LocalRepository(mView.getViewContext().getContentResolver());
        mNetworkRepository = new NetworkRepository();

        if (mLoaderManager.getLoader(SHOW_LOADER) == null) {
            mLoaderManager.initLoader(SHOW_LOADER, null, this).forceLoad();
        } else {
            mLoaderManager.restartLoader(SHOW_LOADER, null, this).forceLoad();
        }
    }


    public void onStop(){

        mView = null;
        mLoaderManager = null;

        mlocalRepository = null;
        mNetworkRepository = null;
    }


    public void saveState(Bundle outState){

        outState.putString(PARAM_SHOWID, mShowID);
    }

    public void restoreState(Bundle inState){

        mShowID = inState.getString(PARAM_SHOWID);
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


    public void refreshShow(){
        //Log.d(LOGTAG, "refreshShow");

        IExecutor<CallbackInteractor> executor = ThreadExecutor.getInstance();

        CallbackInteractor interactor = new UpdateShowInteractor(mShowID, mlocalRepository, mNetworkRepository);
        interactor.setCallback(this);

        executor.execute(interactor);
    }


    // ---------------------------------------------------------------------------------------------
    // PROGRESS CALLBACKS
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onProgressReceived(int actual, int total){
        //Log.d(LOGTAG, "Progress " + actual + "/" + total);
        mView.showLoading((actual*100)/total);
    }


    @Override
    public void onErrorReceived(){
        //Log.d(LOGTAG, "Error");
        mView.showError("");
    }


    @Override
    public void onSuccessReceived(){
        //Log.d(LOGTAG, "Success");
        mView.showSuccess();
        mLoaderManager.getLoader(SHOW_LOADER).onContentChanged();
    }


    // ---------------------------------------------------------------------------------------------
    // LOADER CALLBACKS
    // ---------------------------------------------------------------------------------------------


    @Override
    public Loader<ResponseModel> onCreateLoader(int id, Bundle args) {

        ILocalRepository localRepository = new LocalRepository(mView.getViewContext().getContentResolver());
        INetworkRepository networkRepository = new NetworkRepository();

        IInteractor interactor = new GetShowInteractor(mShowID, localRepository, networkRepository);

        return new AsyncTaskLoaderLoader(mView.getViewContext(), interactor);
    }


    @Override
    public void onLoadFinished(Loader<ResponseModel> loader, ResponseModel data) {
        mResponse = data;
        mView.showResponse(mResponse);
    }


    @Override
    public void onLoaderReset(Loader<ResponseModel> loader) {
        if (mView!= null) {
            mView.reset();
        }
    }
}
