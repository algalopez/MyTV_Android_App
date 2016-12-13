package com.algalopez.mytv.presentation.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;
import com.algalopez.mytv.domain.model.ShowEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/5/16
 */

public class ShowBaseAdapter extends BaseAdapter {


    private final static String LOGTAG = "ShowBaseAdapter";

    private final static int VIEW_HEADER = 0;
    private final static int VIEW_DATA = 1;
    private final static int TOTAL_VIEWS = VIEW_DATA + 1;

    private Context mContext;
    private LayoutInflater mInflater;

    private ShowEntity showHeader = null;
    private ArrayList<SeasonEntity> showData = new ArrayList<>();


    public ShowBaseAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------

    private static class HeaderViewHolder {
        final TextView titleView;
        final TextView releasedView;
        final TextView runtimeView;
        final TextView genreView;
        final TextView directorView;
        final TextView writerView;
        final TextView actorsView;
        final TextView plotView;
        final TextView languageView;
        final TextView countryView;
        final TextView awardsView;
        final TextView metascoreView;
        final TextView imdbRatingView;
        final TextView imdbVotesView;
        final TextView imdbIDView;
        final ImageView imageView;
        final TextView yearView;
        final TextView ratedView;

        HeaderViewHolder(View view) {
            titleView = (TextView) view.findViewById(R.id.header_show_title);
            releasedView = (TextView) view.findViewById(R.id.header_show_released);
            runtimeView = (TextView) view.findViewById(R.id.header_show_runtime);
            genreView = (TextView) view.findViewById(R.id.header_show_genre);
            directorView = (TextView) view.findViewById(R.id.header_show_director);
            writerView = (TextView) view.findViewById(R.id.header_show_writer);
            actorsView = (TextView) view.findViewById(R.id.header_show_actors);
            plotView = (TextView) view.findViewById(R.id.header_show_plot);
            languageView = (TextView) view.findViewById(R.id.header_show_language);
            countryView = (TextView) view.findViewById(R.id.header_show_country);
            awardsView = (TextView) view.findViewById(R.id.header_show_awards);
            metascoreView = (TextView) view.findViewById(R.id.header_show_metascore);
            imdbRatingView = (TextView) view.findViewById(R.id.header_show_imdbrating);
            imdbVotesView = (TextView) view.findViewById(R.id.header_show_imdbvotes);
            imdbIDView = (TextView) view.findViewById(R.id.header_show_imdbid);
            imageView = (ImageView) view.findViewById(R.id.header_show_image);
            yearView = (TextView) view.findViewById(R.id.header_show_year);
            ratedView = (TextView) view.findViewById(R.id.header_show_rated);
        }
    }


    private static class DataViewHolder{
        final TextView seasonView;
        DataViewHolder (View view) {
            seasonView = (TextView)view.findViewById(R.id.item_show_season);
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

        return showData.size() + (showHeader==null?0:1);
    }


    @Override
    public AEntity getItem(int i) {
        if (i==0){
            return showHeader;
        } else {
            return showData.get(i - 1);
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
            convertView = mInflater.inflate(R.layout.header_show, viewGroup, false);
            headerViewHolder = new HeaderViewHolder(convertView);
            convertView.setTag(headerViewHolder);
        } else {
            headerViewHolder = (HeaderViewHolder) convertView.getTag();
        }

        headerViewHolder.titleView.setText(showHeader.getTitle());
        headerViewHolder.releasedView.setText(showHeader.getReleased());
        headerViewHolder.runtimeView.setText(showHeader.getRuntime());
        headerViewHolder.genreView.setText(showHeader.getGenre());
        headerViewHolder.directorView.setText(showHeader.getDirector());
        headerViewHolder.writerView.setText(showHeader.getWriter());
        headerViewHolder.actorsView.setText(showHeader.getActors());
        headerViewHolder.plotView.setText(showHeader.getPlot());
        headerViewHolder.languageView.setText(showHeader.getLanguage());
        headerViewHolder.countryView.setText(showHeader.getCountry());
        headerViewHolder.awardsView.setText(showHeader.getAwards());
        headerViewHolder.metascoreView.setText(showHeader.getMetascore());
        headerViewHolder.imdbRatingView.setText(showHeader.getImdbRating());
        headerViewHolder.imdbVotesView.setText(showHeader.getImdbVotes());
        headerViewHolder.imdbIDView.setText(showHeader.getImdbID());
        headerViewHolder.imageView.setImageBitmap(showHeader.getImage());
        headerViewHolder.yearView.setText(showHeader.getYear());
        headerViewHolder.ratedView.setText(showHeader.getRated());

        return convertView;
    }

    private View getViewData(int position, View convertView, ViewGroup viewGroup){
        DataViewHolder dataViewHolder;
        SeasonEntity seasonEntity = (SeasonEntity) getItem(position);

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.item_show, viewGroup, false);
            dataViewHolder = new DataViewHolder(convertView);
            convertView.setTag(dataViewHolder);
        } else {
            dataViewHolder = (DataViewHolder) convertView.getTag();
        }


        // Append "Season" to item
        StringBuilder sBuilder = new StringBuilder();
        String tagStr = mContext.getString(R.string.season);
        sBuilder.append(tagStr.toUpperCase());
        sBuilder.append(" ");
        sBuilder.append(seasonEntity.getSeason());
        dataViewHolder.seasonView.setText(sBuilder.toString());


        // Change background color if odd
        if (position % 2 == 0){
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_blue_100));
        } else {
            convertView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.light_blue_50));
        }

        return convertView;
    }

    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------


    public void setShow(ResponseModel<ShowEntity, SeasonEntity> model){
        showHeader = model.getHeader();
        showData = model.getData();
        notifyDataSetChanged();
    }


    public void reset(){
        showHeader = null;
        showData = new ArrayList<>();
        notifyDataSetChanged();
    }






}
