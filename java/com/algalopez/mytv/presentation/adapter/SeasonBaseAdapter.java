package com.algalopez.mytv.presentation.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.EpisodeEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/5/16
 */

public class SeasonBaseAdapter  extends BaseAdapter {


    private final static String LOGTAG = "SeasonBaseAdapter";

    private final static int VIEW_HEADER = 0;
    private final static int VIEW_DATA = 1;
    private final static int TOTAL_VIEWS = VIEW_DATA + 1;

    private LayoutInflater mInflater;
    private Context mContext;

    private SeasonEntity seasonHeader = null;
    private ArrayList<EpisodeEntity> seasonData = new ArrayList<>();


    public SeasonBaseAdapter(Context context) {
        this.mInflater = LayoutInflater.from(context);
        this.mContext = context;
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------


    private static class HeaderViewHolder{
        final TextView titleView;
        final TextView seasonView;
        HeaderViewHolder(View view){
            titleView = (TextView)view.findViewById(R.id.header_season_title);
            seasonView = (TextView)view.findViewById(R.id.header_season_season);
        }
    }

    private static class DataViewHolder{
        final TextView titleView;
        final TextView releasedView;
        final TextView episodeView;
        final TextView imdbratingView;
        DataViewHolder(View view){
            titleView = (TextView)view.findViewById(R.id.item_season_title);
            releasedView = (TextView)view.findViewById(R.id.item_season_released);
            episodeView = (TextView)view.findViewById(R.id.item_season_episode);
            imdbratingView = (TextView)view.findViewById(R.id.item_season_imdbrating);
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
        if (position == 0){
            return VIEW_HEADER;
        } else {
            return VIEW_DATA;
        }
    }


    @Override
    public int getCount() {
        return seasonData.size() + (seasonHeader == null?0:1);

    }


    @Override
    public AEntity getItem(int i) {
        if (i==0){
            return seasonHeader;
        } else {
            return seasonData.get(i - 1);
        }
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        switch (getItemViewType(position)){
            case VIEW_HEADER:
                return getViewHeader(convertView, viewGroup);
            case VIEW_DATA:
                return getViewData(position, convertView, viewGroup);
            default:
                return null;
        }
    }


    private View getViewHeader(View convertView, ViewGroup viewGroup){
        HeaderViewHolder headerViewHolder;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.header_season, viewGroup, false);
            headerViewHolder = new HeaderViewHolder(convertView);
            convertView.setTag(headerViewHolder);
        } else {
            headerViewHolder = (HeaderViewHolder) convertView.getTag();
        }

        String title = seasonHeader.getTitle();
        headerViewHolder.titleView.setText(title);


        // Append "Season" to item
        StringBuilder sBuilder = new StringBuilder();
        String tagStr = mContext.getString(R.string.season);
        sBuilder.append(tagStr);
        sBuilder.append(" ");
        sBuilder.append(seasonHeader.getSeason());
        headerViewHolder.seasonView.setText(sBuilder.toString());


        return convertView;
    }

    private View getViewData(int position, View convertView, ViewGroup viewGroup){
        DataViewHolder dataViewHolder;
        EpisodeEntity episodeEntity = (EpisodeEntity) getItem(position);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_season, viewGroup, false);
            dataViewHolder = new DataViewHolder(convertView);
            convertView.setTag(dataViewHolder);
        } else {
            dataViewHolder = (DataViewHolder) convertView.getTag();
        }

        dataViewHolder.titleView.setText(episodeEntity.getTitle());
        dataViewHolder.releasedView.setText(episodeEntity.getReleased());
        dataViewHolder.episodeView.setText(episodeEntity.getEpisode());
        dataViewHolder.imdbratingView.setText(episodeEntity.getImdbRating());


        // Change background color if odd
        if (position % 2 == 0){
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_blue_100));
            //dataViewHolder.episodeView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_blue_100));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_blue_50));
            //dataViewHolder.episodeView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_blue_50));
        }


        return convertView;
    }

    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------


    public void setSeason(ResponseModel<SeasonEntity, EpisodeEntity> model){
        seasonHeader = model.getHeader();
        seasonData = model.getData();
        notifyDataSetChanged();
    }


    public void reset(){
        seasonHeader = null;
        seasonData = new ArrayList<>();
        notifyDataSetChanged();
    }




}
