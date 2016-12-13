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
import com.algalopez.mytv.presentation.activity.ShowActivity;
import com.algalopez.mytv.presentation.adapter.ShowBaseAdapter;
import com.algalopez.mytv.presentation.presenter.ShowPresenter;


public class ShowFragment extends Fragment implements IFragment<ResponseModel>{

    private final static String LOGTAG = "ShowFragment";

    private static final String PARAM_SHOWID = "PARAM_SHOWID";

    private String mShowID;

    private View mRootView;
    private ShowBaseAdapter mAdapter;
    private ShowPresenter mPresenter;
    private IActivity mCallback;



    public ShowFragment() {
        // Required empty public constructor
    }


    public static ShowFragment newInstance(String showID) {
        ShowFragment fragment = new ShowFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_SHOWID, showID);
        fragment.setArguments(args);
        return fragment;
    }


    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mShowID = getArguments().getString(PARAM_SHOWID);
        } else if (getActivity().getIntent()!=null) {
            mShowID = getActivity().getIntent().getExtras().getString(ShowActivity.PARAM_SHOWID);
        } else {
            Log.d(LOGTAG, "No arguments");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        mRootView = inflater.inflate(R.layout.fragment_show, container, false);
        mPresenter = new ShowPresenter(this, getLoaderManager());
        mAdapter = new ShowBaseAdapter(getContext());
        mPresenter.onStart(mShowID);

        ListView showList = (ListView)mRootView.findViewById(R.id.fragment_show_list);
        showList.setOnItemClickListener(onItemClickListener);
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


    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            Log.d(LOGTAG, "Clicked item at position " + position);
            //Toast.makeText(getContext(), "clicked " + String.valueOf(position), Toast.LENGTH_SHORT).show();

            AEntity seasonEntity = (AEntity) adapterView.getItemAtPosition(position);

            if (mCallback != null){
                mCallback.onItemSelected(seasonEntity);
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
                .setTitle(getResources().getString(R.string.dialog_update_show))
                //.setMessage("qweqwe3")
                .setNegativeButton(getResources().getString(R.string.cancel), null)
                .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        mPresenter.refreshShow();
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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_show_progressbar);

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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_show_progressbar);

        int initialProgress = progressBar.getProgress();
        progressBar.setSecondaryProgress(initialProgress);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "secondaryProgress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showSuccess() {

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_show_progressbar);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showResponse(ResponseModel data) {
//        Log.d(LOGTAG, "adapter.setShow()");

        ResponseModel.RepositoryType repoType = data.getRepository();
        switch (repoType){
            case NETWORK:
                mCallback.setNetworkToolbar();
                break;
            case LOCAL:
                mCallback.setLocalToolbar();
                break;
        }

        mAdapter.setShow(data);
    }




}
