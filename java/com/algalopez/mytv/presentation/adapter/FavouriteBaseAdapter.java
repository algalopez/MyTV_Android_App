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
import com.algalopez.mytv.domain.model.FavouriteEntity;
import com.algalopez.mytv.domain.model.ShowEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/5/16
 */

public class FavouriteBaseAdapter extends BaseAdapter{

    private final static String LOGTAG = "FavouriteBaseAdapter";

    private final static int VIEW_DATA = 0;
    private final static int TOTAL_VIEWS = VIEW_DATA + 1;

    private LayoutInflater mInflater;

    private ArrayList<ShowEntity> favouriteData = new ArrayList<>();


    public FavouriteBaseAdapter(Context context) {

        this.mInflater = LayoutInflater.from(context);
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------



    private static class DataViewHolder {
        final TextView titleView;
        final TextView genreView;
        final TextView countryView;
        final TextView yearView;
        final TextView imdbratingView;
        final ImageView imageView;
        final ImageView favouriteView;

        DataViewHolder(View view) {
            titleView = (TextView) view.findViewById(R.id.item_favourite_title);
            genreView = (TextView) view.findViewById(R.id.item_favourite_genre);
            countryView = (TextView) view.findViewById(R.id.item_favourite_country);
            yearView = (TextView) view.findViewById(R.id.item_favourite_year);
            imdbratingView = (TextView) view.findViewById(R.id.item_favourite_imdbrating);
            imageView = (ImageView) view.findViewById(R.id.item_favourite_image);
            favouriteView = (ImageView) view.findViewById(R.id.item_favourite_favourite);
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
        return favouriteData.size();
    }


    @Override
    public ShowEntity getItem(int i) {
        return favouriteData.get(i);
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
            convertView = mInflater.inflate(R.layout.item_favourite, viewGroup, false);
            dataViewHolder = new DataViewHolder(convertView);
            convertView.setTag(dataViewHolder);
        } else {
            dataViewHolder = (DataViewHolder) convertView.getTag();
        }

        dataViewHolder.titleView.setText(showEntity.getTitle());
        dataViewHolder.genreView.setText(showEntity.getGenre());
        dataViewHolder.countryView.setText(showEntity.getCountry());
        dataViewHolder.yearView.setText(showEntity.getYear());
        dataViewHolder.imdbratingView.setText(showEntity.getImdbRating());

        dataViewHolder.imageView.setImageBitmap(showEntity.getImage());

        dataViewHolder.favouriteView.setVisibility(View.GONE);

        return convertView;
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------


    public void setFavourite(ResponseModel<FavouriteEntity, ShowEntity> model){
        favouriteData = model.getData();
        notifyDataSetChanged();
    }


    public void reset(){
        favouriteData = new ArrayList<>();
        notifyDataSetChanged();
    }



}
