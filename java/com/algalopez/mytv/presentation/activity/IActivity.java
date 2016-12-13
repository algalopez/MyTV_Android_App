package com.algalopez.mytv.presentation.activity;

import android.support.v7.widget.Toolbar;

import com.algalopez.mytv.domain.model.AEntity;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/5/16
 */

public interface IActivity {

    enum storage {LOCAL, NETWORK}

    void setLocalToolbar();

    void setNetworkToolbar();

    void onItemSelected(AEntity entity);


}
