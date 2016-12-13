package com.algalopez.mytv.data.local.converter;

import android.content.ContentValues;
import android.database.Cursor;

import com.algalopez.mytv.data.local.database.MyTVDbContract.SeasonEntry;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.SeasonEntity;

import java.util.ArrayList;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */


public class SeasonConverter implements IConverter<SeasonEntity> {


    @Override
    public ContentValues convertToDataModel(SeasonEntity seasonEntity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(SeasonEntry.COLUMN_TITLE, seasonEntity.getTitle());
        contentValues.put(SeasonEntry.COLUMN_SEASON, seasonEntity.getSeason());
        contentValues.put(SeasonEntry.COLUMN_TOTALSEASONS, seasonEntity.getTotalSeasons());
        contentValues.put(SeasonEntry.COLUMN_TOTALEPISODES, seasonEntity.getTotalEpisodes());
        contentValues.put(SeasonEntry.COLUMN_RELEASEDFIRST, seasonEntity.getReleasedFirst());
        contentValues.put(SeasonEntry.COLUMN_RELEASEDLAST, seasonEntity.getReleasedLast());
        contentValues.put(SeasonEntry.COLUMN_SHOWID, seasonEntity.getShowID());

        return contentValues;
    }


    @Override
    public SeasonEntity convertToDomainModel(Cursor data) {

        SeasonEntity seasonEntity = new SeasonEntity();

        if (data == null){
            seasonEntity.setAction(AEntity.ActionType.ERROR);
            return seasonEntity;
        }

        // Cursor is empty
        if (!(data.moveToFirst()) || data.getCount() == 0){
            seasonEntity.setAction(AEntity.ActionType.ERROR);
            return seasonEntity;
        }

        seasonEntity.setTitle(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_TITLE)));
        seasonEntity.setSeason(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_SEASON)));
        seasonEntity.setTotalSeasons(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_TOTALSEASONS)));
        seasonEntity.setTotalEpisodes(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_TOTALEPISODES)));
        seasonEntity.setReleasedFirst(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_RELEASEDFIRST)));
        seasonEntity.setReleasedLast(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_RELEASEDLAST)));
        seasonEntity.setShowID(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_SHOWID)));

        data.close();
        return seasonEntity;
    }


    @Override
    public ArrayList<SeasonEntity> convertAllToDomainModel(Cursor data) {

        ArrayList<SeasonEntity> seasonEntityArray = new ArrayList<>();

        if (data == null){
            return seasonEntityArray;
        }

        // Cursor is empty
        if (!(data.moveToFirst()) || data.getCount() == 0){
            return seasonEntityArray;
        }

        if (data.moveToFirst()) {
            do {
                SeasonEntity seasonEntity = new SeasonEntity();

                seasonEntity.setTitle(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_TITLE)));
                seasonEntity.setSeason(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_SEASON)));
                seasonEntity.setTotalSeasons(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_TOTALSEASONS)));
                seasonEntity.setTotalEpisodes(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_TOTALEPISODES)));
                seasonEntity.setReleasedFirst(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_RELEASEDFIRST)));
                seasonEntity.setReleasedLast(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_RELEASEDLAST)));
                seasonEntity.setShowID(data.getString(data.getColumnIndex(SeasonEntry.COLUMN_SHOWID)));

                seasonEntityArray.add(seasonEntity);

            } while (data.moveToNext());
        }

        data.close();
        return seasonEntityArray;
    }
}
