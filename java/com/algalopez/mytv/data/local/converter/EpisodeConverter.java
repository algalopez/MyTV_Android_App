package com.algalopez.mytv.data.local.converter;

import android.content.ContentValues;
import android.database.Cursor;

import com.algalopez.mytv.data.local.database.MyTVDbContract.EpisodeEntry;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.EpisodeEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */

public class EpisodeConverter implements IConverter<EpisodeEntity> {


    @Override
    public ContentValues convertToDataModel(EpisodeEntity episodeEntity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(EpisodeEntry.COLUMN_TITLE, episodeEntity.getTitle());
        contentValues.put(EpisodeEntry.COLUMN_YEAR, episodeEntity.getYear());
        contentValues.put(EpisodeEntry.COLUMN_RATED, episodeEntity.getRated());
        contentValues.put(EpisodeEntry.COLUMN_RELEASED, episodeEntity.getReleased());

        contentValues.put(EpisodeEntry.COLUMN_SEASON, episodeEntity.getSeason());
        contentValues.put(EpisodeEntry.COLUMN_EPISODE, episodeEntity.getEpisode());

        contentValues.put(EpisodeEntry.COLUMN_RUNTIME, episodeEntity.getRuntime());
        contentValues.put(EpisodeEntry.COLUMN_GENRE, episodeEntity.getGenre());
        contentValues.put(EpisodeEntry.COLUMN_DIRECTOR, episodeEntity.getDirector());
        contentValues.put(EpisodeEntry.COLUMN_WRITER, episodeEntity.getWriter());
        contentValues.put(EpisodeEntry.COLUMN_ACTORS, episodeEntity.getActors());
        contentValues.put(EpisodeEntry.COLUMN_PLOT, episodeEntity.getPlot());
        contentValues.put(EpisodeEntry.COLUMN_LANGUAGE, episodeEntity.getLanguage());
        contentValues.put(EpisodeEntry.COLUMN_COUNTRY, episodeEntity.getCountry());
        contentValues.put(EpisodeEntry.COLUMN_AWARDS, episodeEntity.getAwards());
        contentValues.put(EpisodeEntry.COLUMN_POSTER, episodeEntity.getPoster());
        contentValues.put(EpisodeEntry.COLUMN_METASCORE, episodeEntity.getMetascore());
        contentValues.put(EpisodeEntry.COLUMN_IMDBRATING, episodeEntity.getImdbRating());
        contentValues.put(EpisodeEntry.COLUMN_IMDBVOTES, episodeEntity.getImdbVotes());
        contentValues.put(EpisodeEntry.COLUMN_IMDBID, episodeEntity.getImdbID());
        contentValues.put(EpisodeEntry.COLUMN_SERIESID, episodeEntity.getSeriesID());

        String episodeType;
        switch (episodeEntity.getType()){
            case EPISODE:
                episodeType = EpisodeEntry.KEY_EPISODE;
                break;
            default:
                episodeType = "";
                break;
        }
        contentValues.put(EpisodeEntry.COLUMN_TYPE, episodeType);

        return contentValues;
    }

    @Override
    public EpisodeEntity convertToDomainModel(Cursor data) {

        EpisodeEntity episodeEntity = new EpisodeEntity();

        if (data == null){
            episodeEntity.setAction(AEntity.ActionType.ERROR);
            return episodeEntity;
        }

        // Cursor is empty
        if (!(data.moveToFirst()) || data.getCount() == 0){
            episodeEntity.setAction(AEntity.ActionType.ERROR);
            return episodeEntity;
        }

        episodeEntity.setTitle(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_TITLE)));
        episodeEntity.setYear(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_YEAR)));
        episodeEntity.setRated(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_RATED)));
        episodeEntity.setReleased(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_RELEASED)));

        episodeEntity.setSeason(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_SEASON)));
        episodeEntity.setEpisode(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_EPISODE)));

        episodeEntity.setRuntime(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_RUNTIME)));
        episodeEntity.setGenre(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_GENRE)));
        episodeEntity.setDirector(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_DIRECTOR)));
        episodeEntity.setWriter(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_WRITER)));
        episodeEntity.setActors(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_ACTORS)));
        episodeEntity.setPlot(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_PLOT)));
        episodeEntity.setLanguage(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_LANGUAGE)));
        episodeEntity.setCountry(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_COUNTRY)));
        episodeEntity.setAwards(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_AWARDS)));
        episodeEntity.setPoster(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_POSTER)));
        episodeEntity.setMetascore(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_METASCORE)));
        episodeEntity.setImdbRating(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_IMDBRATING)));
        episodeEntity.setImdbVotes(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_IMDBVOTES)));
        episodeEntity.setImdbID(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_IMDBID)));
        episodeEntity.setSeriesID(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_SERIESID)));

        String episodeType = data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_TYPE));
        if (EpisodeEntry.KEY_EPISODE.equals(episodeType)) {
            episodeEntity.setType(EpisodeEntity.TYPE.EPISODE);
        } else {
            episodeEntity.setType(EpisodeEntity.TYPE.NONE);
        }

        data.close();
        return episodeEntity;
    }


    @Override
    public ArrayList<EpisodeEntity> convertAllToDomainModel(Cursor data) {

        ArrayList<EpisodeEntity> episodeEntityArray = new ArrayList<>();

        if (data == null){
            return episodeEntityArray;
        }

        // Cursor is empty
        if (!(data.moveToFirst()) || data.getCount() == 0){
            return episodeEntityArray;
        }

        if (data.moveToFirst()) {
            do {
                EpisodeEntity episodeEntity = new EpisodeEntity();
                episodeEntity.setTitle(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_TITLE)));
                episodeEntity.setYear(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_YEAR)));
                episodeEntity.setRated(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_RATED)));
                episodeEntity.setReleased(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_RELEASED)));

                episodeEntity.setSeason(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_SEASON)));
                episodeEntity.setEpisode(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_EPISODE)));

                episodeEntity.setRuntime(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_RUNTIME)));
                episodeEntity.setGenre(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_GENRE)));
                episodeEntity.setDirector(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_DIRECTOR)));
                episodeEntity.setWriter(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_WRITER)));
                episodeEntity.setActors(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_ACTORS)));
                episodeEntity.setPlot(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_PLOT)));
                episodeEntity.setLanguage(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_LANGUAGE)));
                episodeEntity.setCountry(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_COUNTRY)));
                episodeEntity.setAwards(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_AWARDS)));
                episodeEntity.setPoster(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_POSTER)));
                episodeEntity.setMetascore(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_METASCORE)));
                episodeEntity.setImdbRating(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_IMDBRATING)));
                episodeEntity.setImdbVotes(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_IMDBVOTES)));
                episodeEntity.setImdbID(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_IMDBID)));
                episodeEntity.setSeriesID(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_SERIESID)));
//                episodeEntity.setType(data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_TYPE)));

                String episodeType = data.getString(data.getColumnIndex(EpisodeEntry.COLUMN_TYPE));
                if (EpisodeEntry.KEY_EPISODE.equals(episodeType)) {
                    episodeEntity.setType(EpisodeEntity.TYPE.EPISODE);
                } else {
                    episodeEntity.setType(EpisodeEntity.TYPE.NONE);
                }

                episodeEntityArray.add(episodeEntity);

            } while (data.moveToNext());
        }
        data.close();
        return episodeEntityArray;
    }
}
