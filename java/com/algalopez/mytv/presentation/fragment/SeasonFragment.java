package com.algalopez.mytv.presentation.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.presentation.activity.IActivity;
import com.algalopez.mytv.presentation.activity.SeasonActivity;
import com.algalopez.mytv.presentation.adapter.SeasonBaseAdapter;
import com.algalopez.mytv.presentation.presenter.SeasonPresenter;



public class SeasonFragment extends Fragment implements IFragment<ResponseModel>{

    private final static String LOGTAG = "SeasonFragment";

    private static final String PARAM_SHOWID = "PARAM_SHOWID";
    private static final String PARAM_SEASON = "PARAM_SEASON";


    private String mShowID;
    private String mSeason;

    private View mRootView;
    private SeasonBaseAdapter mAdapter;
    private SeasonPresenter mPresenter;
    private IActivity mCallback;



    public SeasonFragment() {
        // Required empty public constructor
    }



    public static SeasonFragment newInstance(String showID, String season) {
        SeasonFragment fragment = new SeasonFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_SHOWID, showID);
        args.putString(PARAM_SEASON, season);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShowID = getArguments().getString(PARAM_SHOWID);
            mSeason = getArguments().getString(PARAM_SEASON);
        } else if (getActivity().getIntent() != null){
            mShowID = getActivity().getIntent().getExtras().getString(SeasonActivity.PARAM_SHOWID);
            mSeason = getActivity().getIntent().getExtras().getString(SeasonActivity.PARAM_SEASON);
        } else {
            Log.d(LOGTAG, "No arguments");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_season, container, false);
        mPresenter = new SeasonPresenter(this, getLoaderManager());
        mAdapter = new SeasonBaseAdapter(getContext());
        mPresenter.onStart(mShowID, mSeason);

        ListView seasonList = (ListView)mRootView.findViewById(R.id.fragment_season_list);
        seasonList.setOnItemClickListener(onItemClickListener);
        seasonList.setAdapter(mAdapter);

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


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            //Log.d(LOGTAG, "Clicked item at position " + position);
            // Toast.makeText(getContext(), "clicked " + String.valueOf(position), Toast.LENGTH_SHORT).show();

            AEntity episodeEntity = (AEntity) adapterView.getItemAtPosition(position);
            //Log.d(LOGTAG, "Clicked at position " + position + ", episode " + ((EpisodeEntity)episodeEntity).getEpisode());

            if (mCallback != null){
                mCallback.onItemSelected(episodeEntity);
            }
        }
    };


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
                .setTitle(getResources().getString(R.string.dialog_update_season))
                //.setMessage("qweqwe3")
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.refreshSeason();
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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_season_progressbar);

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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_season_progressbar);

        int initialProgress = progressBar.getProgress();
        progressBar.setSecondaryProgress(initialProgress);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "secondaryProgress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showSuccess() {

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_season_progressbar);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showResponse(ResponseModel data) {
//        Log.d(LOGTAG, "adapter.setSeason()");
        ResponseModel.RepositoryType repoType = data.getRepository();
        switch (repoType){
            case NETWORK:
                mCallback.setNetworkToolbar();
                break;
            case LOCAL:
                mCallback.setLocalToolbar();
                break;
        }
        mAdapter.setSeason(data);
    }
}
