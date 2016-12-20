package com.algalopez.mytv.presentation.presenter;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

import com.algalopez.mytv.data.history.HistoryRepository;
import com.algalopez.mytv.data.omdb.NetworkRepository;
import com.algalopez.mytv.domain.interactor.CallbackInteractor;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.scheduler.IExecutor;
import com.algalopez.mytv.domain.scheduler.ThreadExecutor;
import com.algalopez.mytv.domain.interactor.history.SetHistoryInteractor;
import com.algalopez.mytv.domain.interactor.search.SearchTermInteractor;
import com.algalopez.mytv.domain.scheduler.AsyncTaskLoaderLoader;
import com.algalopez.mytv.domain.interactor.IInteractor;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.domain.repository.IHistoryRepository;
import com.algalopez.mytv.domain.repository.INetworkRepository;
import com.algalopez.mytv.presentation.fragment.IFragment;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/19/16
 */

public class SearchPresenter implements LoaderManager.LoaderCallbacks<ResponseModel>, CallbackInteractor.Callback {

    private final static String LOGTAG = "SearchPresenter";

    private static final int SEARCH_LOADER = 5;

    // STATE
    private static final String PARAM_PAGE = "PARAM_PAGE";
    private static final String PARAM_SEARCHTERM = "PARAM_SEARCHTERM";
    private String mPage = "1";
    private String mSearchTerm = "Stranger";


    private LoaderManager mLoaderManager;
    private IFragment<ResponseModel> mView;





    public SearchPresenter(IFragment<ResponseModel> searchView, LoaderManager loaderManager){

        //Log.d(LOGTAG, "Presenter created");

        mLoaderManager = loaderManager;
        mView = searchView;
    }

    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    public void onStart(String searchterm){
        mSearchTerm = searchterm;

        if (mLoaderManager.getLoader(SEARCH_LOADER) == null) {
            mLoaderManager.initLoader(SEARCH_LOADER, null, this).forceLoad();
        } else {
            mLoaderManager.restartLoader(SEARCH_LOADER, null, this).forceLoad();
        }
    }

    public void onStop(){

        mLoaderManager = null;
    }


    public void saveState(Bundle outState){
        outState.putString(PARAM_PAGE, mPage);
        outState.putString(PARAM_SEARCHTERM, mSearchTerm);
    }

    public void restoreState(Bundle inState){
        mPage = inState.getString(PARAM_PAGE);
        mSearchTerm = inState.getString(PARAM_SEARCHTERM);
    }


    // ---------------------------------------------------------------------------------------------
    // PRESENTER METHODS
    // ---------------------------------------------------------------------------------------------




    // ---------------------------------------------------------------------------------------------
    // PRESENTER METHODS
    // ---------------------------------------------------------------------------------------------


    public void search(String searchTerm){
        //Log.d(LOGTAG, "Performing search: " + searchTerm);

        this.mSearchTerm = searchTerm;

        mView.showLoading(0);

        mLoaderManager.restartLoader(SEARCH_LOADER, null, this).forceLoad();
    }


    public void searchPage(String searchTerm, String page){
        //Log.d(LOGTAG, "Performing searchPage: " + searchTerm);

        this.mSearchTerm = searchTerm;
        this.mPage = page;

        mView.showLoading(0);

        mLoaderManager.restartLoader(SEARCH_LOADER, null, this).forceLoad();
    }


    private void updateHistory(SearchEntity data){
        IHistoryRepository repository = new HistoryRepository(mView.getViewContext());
        CallbackInteractor interactor = new SetHistoryInteractor(data, repository);
        IExecutor executor = ThreadExecutor.getInstance();
        executor.execute(interactor);
    }



    // ---------------------------------------------------------------------------------------------
    // PROGRESS CALLBACKS
    // ---------------------------------------------------------------------------------------------

    @Override
    public void onProgressReceived(int actual, int total) {

        mView.showLoading((actual*100)/total);
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

        INetworkRepository repository = new NetworkRepository();
        IInteractor interactor = new SearchTermInteractor(mSearchTerm, repository);

        return new AsyncTaskLoaderLoader(mView.getViewContext(), interactor);

    }

    @Override
    public void onLoadFinished(Loader<ResponseModel> loader, ResponseModel data) {
//            Log.d(LOGTAG, "On load finished");

        mView.showResponse(data);

        updateHistory((SearchEntity) data.getHeader());

    }

    @Override
    public void onLoaderReset(Loader<ResponseModel> loader) {

        mView.reset();
    }
};


