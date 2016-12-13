package com.algalopez.mytv.domain.scheduler;

import android.os.Handler;
import android.os.Looper;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/20/16
 */



public class MainThreadPoster implements IPoster {

    private static MainThreadPoster sMainThreadExecutor;

    private Handler mHandler;

    private MainThreadPoster() {

        mHandler = new Handler(Looper.getMainLooper());
    }

    public static MainThreadPoster getInstance() {
        if (sMainThreadExecutor == null) {
            sMainThreadExecutor = new MainThreadPoster();
        }
        return sMainThreadExecutor;
    }


    public void post(final Runnable runnable) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                runnable.run();
            }
        });
    }


}
