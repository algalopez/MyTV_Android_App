package com.algalopez.mytv.presentation.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.algalopez.mytv.R;
import com.algalopez.mytv.domain.presentation.ResponseModel;
import com.algalopez.mytv.domain.model.EpisodeEntity;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/5/16
 */

public class EpisodeBaseAdapter extends BaseAdapter{

    private final static String LOGTAG = "EpisodeBaseAdapter";

    private final static int VIEW_HEADER = 0;
    private final static int TOTAL_VIEWS = VIEW_HEADER + 1;

    private LayoutInflater mInflater;
    private Context mContext;

    private EpisodeEntity episodeHeader;


    public EpisodeBaseAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(context);
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------



    private static class HeaderViewHolder{
        final TextView titleView;
        final TextView seasonView;
        final TextView episodeView;
        final TextView ratedView;
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
        HeaderViewHolder(View view){
            titleView = (TextView)view.findViewById(R.id.header_episode_title);
            seasonView = (TextView)view.findViewById(R.id.header_episode_season);
            episodeView = (TextView)view.findViewById(R.id.header_episode_episode);
            ratedView = (TextView)view.findViewById(R.id.header_episode_rated);
            releasedView = (TextView)view.findViewById(R.id.header_episode_released);
            runtimeView = (TextView)view.findViewById(R.id.header_episode_runtime);
            genreView = (TextView)view.findViewById(R.id.header_episode_genre);
            directorView = (TextView)view.findViewById(R.id.header_episode_director);
            writerView = (TextView)view.findViewById(R.id.header_episode_writer);
            actorsView = (TextView)view.findViewById(R.id.header_episode_actors);
            plotView = (TextView)view.findViewById(R.id.header_episode_plot);
            languageView = (TextView)view.findViewById(R.id.header_episode_language);
            countryView = (TextView)view.findViewById(R.id.header_episode_country);
            awardsView = (TextView)view.findViewById(R.id.header_episode_awards);
            metascoreView = (TextView)view.findViewById(R.id.header_episode_metascore);
            imdbRatingView = (TextView)view.findViewById(R.id.header_episode_imdbrating);
            imdbVotesView = (TextView)view.findViewById(R.id.header_episode_imdbvotes);
            imdbIDView = (TextView)view.findViewById(R.id.header_episode_imdbid);
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
        return VIEW_HEADER;
    }


    @Override
    public int getCount() {
        return (episodeHeader==null?0:1);
    }


    @Override
    public EpisodeEntity getItem(int i) {
        return episodeHeader;
    }


    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup viewGroup) {

        HeaderViewHolder headerViewHolder;

        if (convertView == null){
            convertView = mInflater.inflate(R.layout.header_episode, viewGroup, false);
            headerViewHolder = new HeaderViewHolder(convertView);
            convertView.setTag(headerViewHolder);
        } else {
            headerViewHolder = (HeaderViewHolder) convertView.getTag();
        }

        /*

        StringBuilder sBuilder = new StringBuilder();
        String tagStr = mContext.getString(R.string.season);
        sBuilder.append(tagStr);
        sBuilder.append(" ");
        sBuilder.append(seasonHeader.getSeason());
        headerViewHolder.seasonView.setText(sBuilder.toString());
        */
        StringBuilder sBuilder;
        String tagStr;


        headerViewHolder.titleView.setText(episodeHeader.getTitle());

        // Append "Season" to item
        sBuilder = new StringBuilder();
        tagStr = mContext.getString(R.string.season);
        sBuilder.append(tagStr);
        sBuilder.append(" ");
        sBuilder.append(episodeHeader.getSeason());
        headerViewHolder.seasonView.setText(sBuilder.toString());

        // Append "Episode" to item
        sBuilder = new StringBuilder();
        tagStr = mContext.getString(R.string.episode);
        sBuilder.append(tagStr);
        sBuilder.append(" ");
        sBuilder.append(episodeHeader.getEpisode());
        headerViewHolder.episodeView.setText(sBuilder);

        headerViewHolder.ratedView.setText(episodeHeader.getRated());
        headerViewHolder.releasedView.setText(episodeHeader.getReleased());
        headerViewHolder.runtimeView.setText(episodeHeader.getRuntime());
        headerViewHolder.genreView.setText(episodeHeader.getGenre());
        headerViewHolder.directorView.setText(episodeHeader.getDirector());
        headerViewHolder.writerView.setText(episodeHeader.getWriter());
        headerViewHolder.actorsView.setText(episodeHeader.getActors());
        headerViewHolder.plotView.setText(episodeHeader.getPlot());
        headerViewHolder.languageView.setText(episodeHeader.getLanguage());
        headerViewHolder.countryView.setText(episodeHeader.getCountry());
        headerViewHolder.awardsView.setText(episodeHeader.getAwards());
        headerViewHolder.metascoreView.setText(episodeHeader.getMetascore());
        headerViewHolder.imdbRatingView.setText(episodeHeader.getImdbRating());
        headerViewHolder.imdbVotesView.setText(episodeHeader.getImdbVotes());
        headerViewHolder.imdbIDView.setText(episodeHeader.getImdbID());

        return convertView;
    }


    // ---------------------------------------------------------------------------------------------
    //
    // ---------------------------------------------------------------------------------------------


    public void setEpisode(ResponseModel<EpisodeEntity, ?> model){
        episodeHeader = model.getHeader();
        notifyDataSetChanged();
    }


    public void reset(){
        episodeHeader = null;
        notifyDataSetChanged();
    }



}
