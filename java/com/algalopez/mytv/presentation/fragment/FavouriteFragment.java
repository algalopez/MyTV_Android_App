package com.algalopez.mytv.presentation.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.ShowEntity;
import com.algalopez.mytv.presentation.activity.IActivity;
import com.algalopez.mytv.presentation.adapter.FavouriteBaseAdapter;
import com.algalopez.mytv.presentation.presenter.FavouritePresenter;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    9/13/16
 */


public class FavouriteFragment extends Fragment implements IFragment<ResponseModel> {

    private final static String LOGTAG = "FavouriteFragment";

    private View mRootView;
    private FavouriteBaseAdapter mAdapter;
    private FavouritePresenter mPresenter;
    private IActivity mCallback;


    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_favourite, container, false);

        mPresenter = new FavouritePresenter(this, getLoaderManager());
        mAdapter = new FavouriteBaseAdapter(getContext());
        mPresenter.onStart();



        ListView favouriteList = (ListView)mRootView.findViewById(R.id.fragment_favourite_list);
        favouriteList.setOnItemClickListener(onItemClickListener);
        favouriteList.setAdapter(mAdapter);

        return mRootView;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.onStop();
        mPresenter = null;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof IActivity){
            mCallback = (IActivity) context;
        } else {
            mCallback = null;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mCallback = null;
    }


    // ---------------------------------------------------------------------------------------------
    // LISTENERS
    // ---------------------------------------------------------------------------------------------


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

            ShowEntity showEntity = (ShowEntity) adapterView.getItemAtPosition(position);

            if (mCallback != null){
                mCallback.onItemSelected(showEntity);
            }
        }
    };


    // ---------------------------------------------------------------------------------------------
    // VIEW INTERFACE
    // ---------------------------------------------------------------------------------------------

    @Override
    public Context getViewContext() {

        return getContext();
    }


    @Override
    public void reset() {

        mAdapter.reset();
    }


    @Override
    public void showLoading(int progress) {

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_favourite_progressbar);

        if (progress == 0) {
            progressBar.setProgress(progress);
            progressBar.setSecondaryProgress(0);
            return;
        }

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", progress);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();

    }


    @Override
    public void showError(String message) {

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_favourite_progressbar);

        int initialProgress = progressBar.getProgress();
        progressBar.setSecondaryProgress(initialProgress);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "secondaryProgress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();

    }


    @Override
    public void showSuccess() {
        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_favourite_progressbar);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();


    }


    @Override
    public void showResponse(ResponseModel data) {
        Log.d(LOGTAG, "adapter.setfavourite()");
        mAdapter.setFavourite(data);
    }


}
