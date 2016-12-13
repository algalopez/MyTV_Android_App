package com.algalopez.mytv.domain.interactor;

import com.algalopez.mytv.domain.scheduler.MainThreadPoster;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/23/16
 */

public abstract class CallbackInteractor<O> implements IInteractor<O> {


    public enum state {WAITING, RUNNING, FINISHED}

    public interface Callback {
        void onProgressReceived(int actual, int total);
        void onErrorReceived();
        void onSuccessReceived();
    }


    private state mState = state.WAITING;
    private Callback mCallback;



    @Override
    public abstract O run();




    public void setCallback(Callback callback){

        mCallback = callback;
    }


    public void removeCallback(){

        mCallback = null;
    }

    public void setState(state s){

        mState = s;
    }

    public state getState(){

        return mState;
    }


    protected void sendProgress(final int actual, final int total){

        if (mCallback == null){
            return;
        }
        MainThreadPoster mte = MainThreadPoster.getInstance();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCallback.onProgressReceived(actual,total);
            }
        };
        mte.post(runnable);
    }


    protected void sendError(){
        if (mCallback == null){
            return;
        }
        MainThreadPoster mte = MainThreadPoster.getInstance();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCallback.onErrorReceived();
            }
        };
        mte.post(runnable);
    }


    protected void sendSuccess(){
        if (mCallback == null){
            return;
        }
        MainThreadPoster mte = MainThreadPoster.getInstance();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                mCallback.onSuccessReceived();
            }
        };
        mte.post(runnable);
    }



}
