package com.algalopez.mytv.presentation.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.algalopez.mytv.data.local.LocalRepository;
import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.scheduler.ThreadExecutor;
import com.algalopez.mytv.domain.interactor.favourite.GetAllFavouriteInteractor;
import com.algalopez.mytv.domain.interactor.favourite.RemoveAllFavouriteInteractor;
import com.algalopez.mytv.domain.scheduler.AsyncTaskLoaderLoader;
import com.algalopez.mytv.domain.repository.ILocalRepository;
import com.algalopez.mytv.presentation.fragment.IFragment;

/**
 * Created by algalopez on 9/13/16.
 *
 */

public class FavouritePresenter implements LoaderManager.LoaderCallbacks<ResponseModel>, CallbackInteractor.Callback{

    private final static String LOGTAG = "FavouritePresenter";

    private static final int FAVOURITE_LOADER = 1;

    private IFragment<ResponseModel> mView;
    private LoaderManager mLoaderManager;

    public FavouritePresenter(IFragment<ResponseModel> favouriteView, LoaderManager loaderManager){
        this.mView = favouriteView;
        this.mLoaderManager = loaderManager;
    }


    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    public void onStart(){
        //Log.d(LOGTAG, "onStart");
        if (mLoaderManager.getLoader(FAVOURITE_LOADER) == null) {
            mLoaderManager.initLoader(FAVOURITE_LOADER, null, this).forceLoad();
        } else {
            mLoaderManager.restartLoader(FAVOURITE_LOADER, null, this).forceLoad();
        }
    }


    public void onStop(){
        mView = null;
        mLoaderManager = null;

    }


    public void saveState(Bundle outState){
        // No state to save
    }


    public void restoreState(Bundle inState){
        // No state to restore
    }


    // ---------------------------------------------------------------------------------------------
    // PRESENTER METHODS
    // ---------------------------------------------------------------------------------------------


    public void getAllFavourite(){
        //Log.d(LOGTAG, "getAllFavourite");

        mLoaderManager.restartLoader(FAVOURITE_LOADER, null, this).forceLoad();
    }


    public void removeAllFavourite(){
        //Log.d(LOGTAG, "removeAllFavourite");
        ILocalRepository loc = new LocalRepository(mView.getViewContext().getContentResolver());

        CallbackInteractor interactor = new RemoveAllFavouriteInteractor(loc);
        ThreadExecutor.getInstance().execute(interactor);

        mLoaderManager.getLoader(FAVOURITE_LOADER).onContentChanged();
    }


    // ---------------------------------------------------------------------------------------------
    // PROGRESS CALLBACKS
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onProgressReceived(int actual, int total) {
        //Log.d(LOGTAG, "onProgressReceived");
        mView.showLoading((actual*100)/total);
    }


    @Override
    public void onErrorReceived() {
        //Log.d(LOGTAG, "onErrorReceived");
        mView.showError("");
    }


    @Override
    public void onSuccessReceived() {
        //Log.d(LOGTAG, "onSuccessReceived");
        mView.showSuccess();
    }


    // ---------------------------------------------------------------------------------------------
    // LOADER CALLBACKS
    // ---------------------------------------------------------------------------------------------


    @Override
    public Loader<ResponseModel> onCreateLoader(int id, Bundle args) {

        //Log.d(LOGTAG, "onCreateLoader");
        ILocalRepository repository = new LocalRepository(mView.getViewContext().getContentResolver());
        CallbackInteractor interactor = new GetAllFavouriteInteractor(repository);
        //interactor.setCallback(this);

        return new AsyncTaskLoaderLoader(mView.getViewContext(), interactor);
    }


    @Override
    public void onLoadFinished(Loader<ResponseModel> loader, ResponseModel data) {

        mView.showResponse(data);
    }


    @Override
    public void onLoaderReset(Loader loader) {
        if (mView!= null) {
            mView.reset();
        }
    }
}
