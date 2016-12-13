package com.algalopez.mytv.domain.interactor.test;

import android.util.Log;

import com.algalopez.mytv.domain.interactor.CallbackInteractor;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/20/16
 */

public class TestInteractor extends CallbackInteractor<Integer> {

    public TestInteractor callback(){
        return this;
    }






    @Override
    public Integer run() {

        sendProgress(0,100);

        for (int j = 0; j < 100; j++){

            try {
                Thread.sleep(100);
            } catch (Exception e){
                Log.d("Test", "Thread sleep exception");
            }
            sendProgress(j, 100);
        }

        sendSuccess();

        return null;
    }
}
