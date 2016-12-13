package com.algalopez.mytv.presentation.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.algalopez.mytv.data.history.HistoryRepository;
import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.interactor.IInteractor;
import com.algalopez.mytv.domain.interactor.history.GetAllHistoryInteractor;
import com.algalopez.mytv.domain.scheduler.AsyncTaskLoaderLoader;
import com.algalopez.mytv.domain.repository.IHistoryRepository;
import com.algalopez.mytv.presentation.fragment.IFragment;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/6/16
 */

public class LookPresenter implements LoaderManager.LoaderCallbacks<ResponseModel>, CallbackInteractor.Callback{


    private final static String LOGTAG = "LookPresenter";

    private static final int LOOK_LOADER = 6;

    private IFragment<ResponseModel> mView;
    private LoaderManager mLoaderManager;

    public LookPresenter(IFragment<ResponseModel> lookView, LoaderManager loaderManager) {
        this.mView = lookView;
        this.mLoaderManager = loaderManager;
    }


    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------



    public void onStart() {

        if (mLoaderManager.getLoader(LOOK_LOADER) == null) {
            mLoaderManager.initLoader(LOOK_LOADER, null, this).forceLoad();
        } else {
            mLoaderManager.restartLoader(LOOK_LOADER, null, this).forceLoad();
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
        IHistoryRepository repository = new HistoryRepository(mView.getViewContext());
        IInteractor interactor = new GetAllHistoryInteractor(repository);
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
