package com.algalopez.mytv.domain.scheduler;

import android.util.Log;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */

public class ThreadExecutor implements IExecutor<CallbackInteractor> {

    private static final String LOGTAG = "ThreadExecutor";

    private static ThreadExecutor sThreadExecutor;

    private static boolean isThreadRunning;
    private Thread runningThread;


    private ThreadExecutor(){
        isThreadRunning = false;
    }

    public static ThreadExecutor getInstance() {
        if (sThreadExecutor == null) {
            sThreadExecutor = new ThreadExecutor();
        }
        return sThreadExecutor;
    }


    @Override
    public void execute(final CallbackInteractor interactor) {
        if (isThreadRunning){
            Log.d(LOGTAG, "One thread is already running");
            return;
        }

        runningThread = new Thread(new Runnable() {
            @Override
            public void run() {
                interactor.setState(CallbackInteractor.state.RUNNING);
                isThreadRunning = true;
                interactor.run();
                isThreadRunning = false;
                interactor.setState(CallbackInteractor.state.FINISHED);
            }
        });
        runningThread.start();
    }





}
