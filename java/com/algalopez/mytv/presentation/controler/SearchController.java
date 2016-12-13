package com.algalopez.mytv.presentation.controler;

import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;

import com.algalopez.mytv.R;
import com.algalopez.mytv.presentation.presenter.SearchPresenter;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/24/16
 */

public class SearchController implements OnClickListener, OnItemClickListener, OnScrollListener, OnKeyListener {

    private final static String LOGTAG = "SearchController";

    private static final String TAG_BUTTON = "1";



    private final static int SCROLL_NO = SCROLL_STATE_IDLE;
    private final static int SCROLL_YES = SCROLL_STATE_TOUCH_SCROLL;
    private final static int SCROLL_FLING = SCROLL_STATE_FLING;

    private SearchPresenter mPresenter;
    private View mRootView;



    private boolean onScrollEnd = false;
    private boolean onScrollChange = true;




    public SearchController(SearchPresenter presenter, View rootView){
        this.mPresenter = presenter;
        this.mRootView = rootView;
    }






    @Override
    public void onClick(View view) {
        //String tag = view.getTag().toString();

//        EditText searchEditText = (EditText) ((View)view.getParent()).findViewById(R.id.search_editText);
//        String searchTerm = searchEditText.getText().toString();

//        mPresenter.performSearch(searchTerm);
    }



    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {

        return false;

    }



    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        //String tag = view.getTag().toString();

//        int itemType = adapterView.getAdapter().getItemViewType(position);
//        switch (itemType) {
//            case SearchBaseAdapter.TYPE_SEARCH:
//                SearchEntity.Data searchItem = (SearchEntity.Data)adapterView.getAdapter().getItem(position);
//                mPresenter.GetShowInteractor(searchItem.getImdbID());
//                break;
//            case SearchBaseAdapter.TYPE_SHOW:
//                ShowEntity.Data showItem = (ShowEntity.Data)adapterView.getAdapter().getItem(position);
//                mPresenter.getSeason(showItem.getShowID(), showItem.getSeason());
//                break;
//            case SearchBaseAdapter.TYPE_SEASON:
//                SeasonEntity.Data seasonItem = (SeasonEntity.Data)adapterView.getAdapter().getItem(position);
//                mPresenter.getEpisode(seasonItem.getImdbID(), seasonItem.getSeason(), seasonItem.getEpisode());
//                break;
//            default:
//                Log.d(LOGTAG, "searchListClick Invalid type");
//        }
    }


    // Callback method to be invoked while the list view or grid view is being scrolled.
    @Override
    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
        //Log.d(LOGTAG, "onScrollStateChanged " + scrollState);

//        if ((!onScrollChange) && (onScrollEnd) && (scrollState == SCROLL_YES)){
//            Log.d(LOGTAG, "Load more content");
//            //mPresenter.appendSearch();
//        } else if ((onScrollChange) && (onScrollEnd)){
//            onScrollChange = false;
//        }
    }


    // Callback method to be invoked when the list or grid has been scrolled.
    @Override
    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        //Log.d(LOGTAG, "onScroll " + firstVisibleItem + " - " + visibleItemCount + " - " + totalItemCount);

//        onScrollChange = true;
//        onScrollEnd = firstVisibleItem + visibleItemCount == totalItemCount;
    }


}
