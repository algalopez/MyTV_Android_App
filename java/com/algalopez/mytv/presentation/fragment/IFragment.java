package com.algalopez.mytv.presentation.fragment;

import android.content.Context;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/28/16
 */

public interface IFragment<O> {

    Context getViewContext();

    void reset();

    void showLoading(int progress);

    void showError(String message);

    void showSuccess();

    void showResponse(O data);


}
