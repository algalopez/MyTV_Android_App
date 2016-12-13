package com.algalopez.mytv.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.SearchEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/6/16
 */

public class LookBaseAdapter extends BaseAdapter {

    private final static String LOGTAG = "LookBaseAdapter";

    private final static int VIEW_DATA = 0;
    private final static int TOTAL_VIEWS = VIEW_DATA + 1;

    private LayoutInflater mInflater;
    private Context mContext;

    private ArrayList<SearchEntity> lookData = new ArrayList<>();

    public LookBaseAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------


    private static class DataViewHolder {
        final TextView searchTermView;
        final TextView totalResultsView;

        DataViewHolder(View view) {
            searchTermView = (TextView) view.findViewById(R.id.item_look_searchterm);
            totalResultsView = (TextView) view.findViewById(R.id.item_look_result);
        }
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------

    @Override
    public int getViewTypeCount() {

        return TOTAL_VIEWS;
    }


    @Override
    public int getItemViewType(int position) {

        return VIEW_DATA;
    }


    @Override
    public int getCount() {

        return lookData.size();
    }

    @Override
    public SearchEntity getItem(int i) {

        return lookData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {
        DataViewHolder dataViewHolder;
        SearchEntity searchEntity = getItem(position);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_look, viewGroup, false);
            dataViewHolder = new DataViewHolder(convertView);
            convertView.setTag(dataViewHolder);
        } else {
            dataViewHolder = (DataViewHolder) convertView.getTag();
        }

        dataViewHolder.searchTermView.setText(searchEntity.getSearchTerm().toUpperCase());


        // Append "Total Results" to item
        StringBuilder sBuilder = new StringBuilder();
        String tagStr = mContext.getString(R.string.found);
        sBuilder.append(tagStr).append(": ");
        sBuilder.append(searchEntity.getTotalResults()).append(" ");
        sBuilder.append(mContext.getString(R.string.results));
        dataViewHolder.totalResultsView.setText(sBuilder.toString());

        return convertView;
    }

    // ---------------------------------------------------------------------------------------------
    // PUBLIC METHODS
    // ---------------------------------------------------------------------------------------------


    public void setLook(ResponseModel<?, SearchEntity> model){
        lookData = model.getData();
        notifyDataSetChanged();
    }


    public void reset(){
        lookData = new ArrayList<>();
        notifyDataSetChanged();
    }
}
