package com.algalopez.mytv.presentation.fragment;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.presentation.activity.IActivity;
import com.algalopez.mytv.presentation.adapter.LookBaseAdapter;
import com.algalopez.mytv.presentation.presenter.LookPresenter;


public class LookFragment extends Fragment implements IFragment<ResponseModel>{

    private final static String LOGTAG = "LookFragment";


    private View mRootView;
    private LookBaseAdapter mAdapter;
    private LookPresenter mPresenter;
    private IActivity mCallback;

    public LookFragment() {
        // Required empty public constructor
    }


    public static LookFragment newInstance() {
        return new LookFragment();
    }



    // ---------------------------------------------------------------------------------------------
    // FRAGMENT LIFECYCLE
    // ---------------------------------------------------------------------------------------------



    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        mRootView = inflater.inflate(R.layout.fragment_look, container, false);
        mPresenter = new LookPresenter(this, getLoaderManager());
        mAdapter = new LookBaseAdapter(getContext());
        mPresenter.onStart();

        ListView lookList = (ListView)mRootView.findViewById(R.id.fragment_look_list);
        lookList.setOnItemClickListener(onItemClickListener);
        lookList.setAdapter(mAdapter);

        return mRootView;
    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        EditText editText = (EditText)getActivity().findViewById(R.id.toolbar_look_searchbox);
        editText.setOnEditorActionListener(onEditorActionListener);
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

            SearchEntity searchEntity = (SearchEntity) adapterView.getItemAtPosition(position);

            if (mCallback != null){
                mCallback.onItemSelected(searchEntity);
            }
        }
    };



    EditText.OnEditorActionListener onEditorActionListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
//            Log.d(LOGTAG, "Search " + textView.getText() + ", action " + actionId + ", keyEvent " + keyEvent);

            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                SearchEntity searchEntity = new SearchEntity();
                searchEntity.setSearchTerm(textView.getText().toString());
                if (mCallback != null){
                    mCallback.onItemSelected(searchEntity);
                }
            }
            return true;
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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_look_progressbar);

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

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_look_progressbar);

        int initialProgress = progressBar.getProgress();
        progressBar.setSecondaryProgress(initialProgress);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "secondaryProgress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showSuccess() {

        ProgressBar progressBar = (ProgressBar)mRootView.findViewById(R.id.fragment_look_progressbar);

        ObjectAnimator animation = ObjectAnimator.ofInt(progressBar, "progress", 100);
        animation.setDuration(500); // 0.5 second
        animation.setInterpolator(new LinearInterpolator());
        animation.start();
    }


    @Override
    public void showResponse(ResponseModel data) {

        Log.d(LOGTAG, "adapter.setShow()");

        mAdapter.setLook(data);
    }
}
