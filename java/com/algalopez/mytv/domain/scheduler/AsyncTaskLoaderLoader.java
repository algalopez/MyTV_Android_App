package com.algalopez.mytv.domain.scheduler;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;

import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.interactor.IInteractor;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/26/16
 */

public class AsyncTaskLoaderLoader extends AsyncTaskLoader<ResponseModel> implements ILoader {

    private final static String LOGTAG = "ATLExecutor";

//    private final static String LOADER_MESSAGE = "LOADER_MESSAGE";
//    private LoaderBroadcastReceiver loaderBroadcastReceiver = null;

    private IInteractor mInteractor;


    public AsyncTaskLoaderLoader(Context context, IInteractor interactor) {
        super(context);
//        Log.d(LOGTAG, "Constructor");

        mInteractor = interactor;
        //forceLoad();
    }

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        forceLoad();
    }

    //
//
//    @Override
//    protected void onStartLoading() {
//        super.onStartLoading();
//
//        if(loaderBroadcastReceiver == null)
//        {
//            loaderBroadcastReceiver = new LoaderBroadcastReceiver(this);
//            LocalBroadcastManager.getInstance(getContext()).registerReceiver(loaderBroadcastReceiver, new IntentFilter(LOADER_MESSAGE));
//        }
//    }
//
//
//    @Override
//    protected void onReset() {
//        super.onReset();
//        if (loaderBroadcastReceiver != null) {
//            getContext().unregisterReceiver(loaderBroadcastReceiver);
//            loaderBroadcastReceiver = null;
//        }
//    }


    @Override
    public ResponseModel loadInBackground() {
//        Log.d(LOGTAG, "loadInBackground()");
        return (ResponseModel) mInteractor.run();
    }


















//
//    public class LoaderBroadcastReceiver extends BroadcastReceiver
//    {
//        private AsyncTaskLoaderLoader loader;
//
//        public LoaderBroadcastReceiver(AsyncTaskLoaderLoader loader)
//        {
//            this.loader = loader;
//        }
//
//        @Override
//        public void onReceive(Context context, Intent intent)
//        {
//            this.loader.onContentChanged();
//        }
//    }






}
