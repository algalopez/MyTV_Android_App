package com.algalopez.mytv.presentation.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.presentation.activity.IActivity;
import com.algalopez.mytv.presentation.activity.SearchActivity;
import com.algalopez.mytv.presentation.adapter.SearchBaseAdapter;
import com.algalopez.mytv.presentation.controler.SearchController;
import com.algalopez.mytv.presentation.presenter.SearchPresenter;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    9/21/16
 */

public class SearchFragment extends Fragment implements IFragment<ResponseModel> {

    private final static String LOGTAG = "SearchFragment";

    private static final String PARAM_SEARCHTERM = "PARAM_SEARCHTERM";

    private String mSearchTerm;


    private View mRootView;
    private SearchBaseAdapter mAdapter;
    private SearchPresenter mPresenter;
    private SearchController mController;
    private IActivity mCallback;


    public SearchFragment() {
        // Required empty public constructor
    }


    public static SearchFragment newInstance(String searchTerm) {
        SearchFragment fragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString(PARAM_SEARCHTERM, searchTerm);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mSearchTerm = getArguments().getString(PARAM_SEARCHTERM);
        } else if (getActivity().getIntent()!=null) {
            mSearchTerm = getActivity().getIntent().getExtras().getString(SearchActivity.PARAM_SEARCHTERM);
        } else {
            Log.d(LOGTAG, "No arguments");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mRootView = inflater.inflate(R.layout.fragment_search, container, false);

        mPresenter = new SearchPresenter(this, getLoaderManager());
        mController = new SearchController(mPresenter, mRootView);
        mAdapter = new SearchBaseAdapter(getContext());
        mPresenter.onStart(mSearchTerm);

        // List OnScroll and OnItemClick Listeners
        ListView searchListView = (ListView)mRootView.findViewById(R.id.search_list);
        searchListView.setAdapter(mAdapter);
        searchListView.setOnItemClickListener(onItemClickListener);
//        searchListView.setOnScrollListener(mController);

        return mRootView;
    }



    @Override
    public void onDestroyView() {

        mPresenter.onStop();

        // List OnScroll and OnItemClick Listeners
        ListView searchListView = (ListView)mRootView.findViewById(R.id.search_list);
        searchListView.setOnItemClickListener(null);
        searchListView.setOnScrollListener(null);

        mPresenter = null;
        mController = null;

        super.onDestroyView();
    }



    @Override
    public void onSaveInstanceState(Bundle outState) {
        mPresenter.saveState(outState);
        super.onSaveInstanceState(outState);
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

            AEntity showEntity = (AEntity) adapterView.getItemAtPosition(position);

            if (mCallback != null){
                mCallback.onItemSelected(showEntity);
            }
        }
    };


    public void onFavouriteClick(){

        Log.d(LOGTAG, "onFavouriteClick()");
    }


    public void onRefreshClick(){

        Log.d(LOGTAG, "onRefreshClick()");
    }





    // ---------------------------------------------------------------------------------------------
    // VIEW INTERFACE
    // ---------------------------------------------------------------------------------------------


    @Override
    public Context getViewContext() {

        return getContext();
    }


    @Override
    public void reset(){

        mAdapter.reset();
    }


    @Override
    public void showLoading(int progress) {

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_search_progressbar);

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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_search_progressbar);

        int initialProgress = progressBar.getProgress();
        progressBar.setSecondaryProgress(initialProgress);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "secondaryProgress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showSuccess() {

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_search_progressbar);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showResponse(ResponseModel data) {
//        Log.d(LOGTAG, "showSearch");
        mAdapter.setSearch(data);
    }


}