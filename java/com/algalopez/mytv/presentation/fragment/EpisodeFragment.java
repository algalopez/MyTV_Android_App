package com.algalopez.mytv.presentation.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.presentation.activity.EpisodeActivity;
import com.algalopez.mytv.presentation.activity.IActivity;
import com.algalopez.mytv.presentation.adapter.EpisodeBaseAdapter;
import com.algalopez.mytv.presentation.presenter.EpisodePresenter;


public class EpisodeFragment extends Fragment implements IFragment<ResponseModel>{

    private final static String LOGTAG = "EpisodeFragment";

    private static final String PARAM_SHOWID = "PARAM_SHOWID";
    private static final String PARAM_SEASON = "PARAM_SEASON";
    private static final String PARAM_EPISODE = "PARAM_EPISODE";


    private String mShowID;
    private String mSeason;
    private String mEpisode;

    private View mRootView;
    private EpisodeBaseAdapter mAdapter;
    private EpisodePresenter mPresenter;
    private IActivity mCallback;



    public EpisodeFragment() {
        // Required empty public constructor
    }

    public static EpisodeFragment newInstance(String showID, String season, String episode) {
        EpisodeFragment fragment = new EpisodeFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_SHOWID, showID);
        args.putString(PARAM_SEASON, season);
        args.putString(PARAM_EPISODE, episode);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShowID = getArguments().getString(PARAM_SHOWID);
            mSeason = getArguments().getString(PARAM_SEASON);
            mEpisode = getArguments().getString(PARAM_EPISODE);
        } else if (getActivity().getIntent() != null){
            mShowID = getActivity().getIntent().getExtras().getString(EpisodeActivity.PARAM_SHOWID);
            mSeason = getActivity().getIntent().getExtras().getString(EpisodeActivity.PARAM_SEASON);
            mEpisode = getActivity().getIntent().getExtras().getString(EpisodeActivity.PARAM_EPISODE);
        } else {
            Log.d(LOGTAG, "No arguments");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_episode, container, false);
        mPresenter = new EpisodePresenter(this, getLoaderManager());
        mAdapter = new EpisodeBaseAdapter(getContext());
        mPresenter.onStart(mShowID, mSeason, mEpisode);
        //Log.d(LOGTAG, "mPresenter.onCreateView(" + mShowID + ", "+mSeason+", "+mEpisode+")");


        ListView showList = (ListView)mRootView.findViewById(R.id.fragment_episode_list);
        showList.setAdapter(mAdapter);

        return mRootView;
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



    public void onFavouriteClick(){
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.dialog_save_show))
                //.setMessage("qweqwe1")
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.favouriteShow();
                    }
                })
                .create();
        dialog.show();
    }


    public void onUnFavouriteClick(){
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.dialog_remove_show))
                //.setMessage("qweqwe2")
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.unFavouriteShow();
                    }
                })
                .create();
        dialog.show();
    }


    public void onRefreshClick(){
        AlertDialog dialog = new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.dialog_update_episode))
                //.setMessage("qweqwe3")
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.refreshEpisode();
                    }
                })
                .create();
        dialog.show();
    }


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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_episode_progressbar);

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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_episode_progressbar);

        int initialProgress = progressBar.getProgress();
        progressBar.setSecondaryProgress(initialProgress);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "secondaryProgress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showSuccess() {

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_episode_progressbar);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showResponse(ResponseModel data) {
//        Log.d(LOGTAG, "adapter.setEpisode()");
        ResponseModel.RepositoryType repoType = data.getRepository();
        switch (repoType){
            case NETWORK:
                mCallback.setNetworkToolbar();
                break;
            case LOCAL:
                mCallback.setLocalToolbar();
                break;
        }
        mAdapter.setEpisode(data);
    }
}
