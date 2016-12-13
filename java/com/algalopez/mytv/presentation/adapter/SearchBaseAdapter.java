package com.algalopez.mytv.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.SearchEntity;
import com.algalopez.mytv.domain.model.ShowEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/5/16
 */

public class SearchBaseAdapter extends BaseAdapter {


    private final static String LOGTAG = "SearchBaseAdapter";

    private final static int VIEW_DATA = 0;
    private final static int TOTAL_VIEWS = VIEW_DATA + 1;

    private LayoutInflater mInflater;

    private ArrayList<ShowEntity> searchData = new ArrayList<>();


    public SearchBaseAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------



    private static class DataViewHolder {
        final TextView titleView;
        final TextView typeView;
        final ImageView imageView;
        final ImageView favouriteView;

        DataViewHolder(View view) {
            titleView = (TextView) view.findViewById(R.id.item_search_title);
            typeView = (TextView) view.findViewById(R.id.item_search_type);
            imageView = (ImageView) view.findViewById(R.id.item_search_image);
            favouriteView = (ImageView) view.findViewById(R.id.item_search_favourite);
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
        return searchData.size();
    }


    @Override
    public ShowEntity getItem(int i) {
        return searchData.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        DataViewHolder dataViewHolder;
        ShowEntity showEntity = getItem(position);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_search, viewGroup, false);
            dataViewHolder = new DataViewHolder(convertView);
            convertView.setTag(dataViewHolder);
        } else {
            dataViewHolder = (DataViewHolder) convertView.getTag();
        }

        dataViewHolder.titleView.setText(showEntity.getTitle());
        dataViewHolder.typeView.setText(showEntity.getType().toString());

        dataViewHolder.imageView.setImageBitmap(showEntity.getImage());

        return convertView;
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------


    public void setSearch(ResponseModel<SearchEntity, ShowEntity> model){
        searchData = model.getData();
        notifyDataSetChanged();
    }


    public void reset(){
        searchData = new ArrayList<>();
        notifyDataSetChanged();
    }



}
