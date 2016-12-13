package com.algalopez.mytv.data.local.converter;

import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.algalopez.mytv.data.local.database.MyTVDbContract;
import com.algalopez.mytv.data.local.database.MyTVDbContract.ShowEntry;
import com.algalopez.mytv.domain.model.AEntity;
import com.algalopez.mytv.domain.model.ShowEntity;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */


public class ShowConverter implements IConverter<ShowEntity> {


    @Override
    public ContentValues convertToDataModel(ShowEntity showEntity) {

        ContentValues contentValues = new ContentValues();
        contentValues.put(ShowEntry.COLUMN_TITLE, showEntity.getTitle());
        contentValues.put(ShowEntry.COLUMN_YEAR, showEntity.getYear());
        contentValues.put(ShowEntry.COLUMN_RATED, showEntity.getRated());
        contentValues.put(ShowEntry.COLUMN_RELEASED, showEntity.getReleased());
        contentValues.put(ShowEntry.COLUMN_RUNTIME, showEntity.getRuntime());
        contentValues.put(ShowEntry.COLUMN_GENRE, showEntity.getGenre());
        contentValues.put(ShowEntry.COLUMN_DIRECTOR, showEntity.getDirector());
        contentValues.put(ShowEntry.COLUMN_WRITER, showEntity.getWriter());
        contentValues.put(ShowEntry.COLUMN_ACTORS, showEntity.getActors());
        contentValues.put(ShowEntry.COLUMN_PLOT, showEntity.getPlot());
        contentValues.put(ShowEntry.COLUMN_LANGUAGE, showEntity.getLanguage());
        contentValues.put(ShowEntry.COLUMN_COUNTRY, showEntity.getCountry());
        contentValues.put(ShowEntry.COLUMN_AWARDS, showEntity.getAwards());
        contentValues.put(ShowEntry.COLUMN_POSTER, showEntity.getPoster());
        contentValues.put(ShowEntry.COLUMN_METASCORE, showEntity.getMetascore());
        contentValues.put(ShowEntry.COLUMN_IMDBRATING, showEntity.getImdbRating());
        contentValues.put(ShowEntry.COLUMN_IMDBVOTES, showEntity.getImdbVotes());
        contentValues.put(ShowEntry.COLUMN_IMDBID, showEntity.getImdbID());
        contentValues.put(ShowEntry.COLUMN_TOTALSEASONS, showEntity.getTotalSeasons());

        String showtype;
        switch (showEntity.getType()){
            case MOVIE:
                showtype = ShowEntry.KEY_MOVIE;
                break;
            case SERIES:
                showtype = ShowEntry.KEY_SERIES;
                break;
            default:
                showtype = "";
                break;
        }
        contentValues.put(ShowEntry.COLUMN_TYPE, showtype);

        Bitmap image = showEntity.getImage();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 100, bos);
        byte[] imageArray = bos.toByteArray();
        contentValues.put(ShowEntry.COLUMN_IMAGE, imageArray);

        return contentValues;
    }


    @Override
    public ShowEntity convertToDomainModel(Cursor data) {

        ShowEntity showEntity = new ShowEntity();

        if (data == null){
            showEntity.setAction(AEntity.ActionType.ERROR);
            return showEntity;
        }

        // Cursor is empty
        if (!(data.moveToFirst()) || data.getCount() == 0){
            showEntity.setAction(AEntity.ActionType.ERROR);
            return showEntity;
        }

        showEntity.setTitle(data.getString(data.getColumnIndex(ShowEntry.COLUMN_TITLE)));
        showEntity.setYear(data.getString(data.getColumnIndex(ShowEntry.COLUMN_YEAR)));
        showEntity.setRated(data.getString(data.getColumnIndex(ShowEntry.COLUMN_RATED)));
        showEntity.setReleased(data.getString(data.getColumnIndex(ShowEntry.COLUMN_RELEASED)));
        showEntity.setRuntime(data.getString(data.getColumnIndex(ShowEntry.COLUMN_RUNTIME)));
        showEntity.setGenre(data.getString(data.getColumnIndex(ShowEntry.COLUMN_GENRE)));
        showEntity.setDirector(data.getString(data.getColumnIndex(ShowEntry.COLUMN_DIRECTOR)));
        showEntity.setWriter(data.getString(data.getColumnIndex(ShowEntry.COLUMN_WRITER)));
        showEntity.setActors(data.getString(data.getColumnIndex(ShowEntry.COLUMN_ACTORS)));
        showEntity.setPlot(data.getString(data.getColumnIndex(ShowEntry.COLUMN_PLOT)));
        showEntity.setLanguage(data.getString(data.getColumnIndex(ShowEntry.COLUMN_LANGUAGE)));
        showEntity.setCountry(data.getString(data.getColumnIndex(ShowEntry.COLUMN_COUNTRY)));
        showEntity.setAwards(data.getString(data.getColumnIndex(ShowEntry.COLUMN_AWARDS)));
        showEntity.setPoster(data.getString(data.getColumnIndex(ShowEntry.COLUMN_POSTER)));
        showEntity.setMetascore(data.getString(data.getColumnIndex(ShowEntry.COLUMN_METASCORE)));
        showEntity.setImdbRating(data.getString(data.getColumnIndex(ShowEntry.COLUMN_IMDBRATING)));
        showEntity.setImdbVotes(data.getString(data.getColumnIndex(ShowEntry.COLUMN_IMDBVOTES)));
        showEntity.setImdbID(data.getString(data.getColumnIndex(ShowEntry.COLUMN_IMDBID)));
        showEntity.setTotalSeasons(data.getString(data.getColumnIndex(ShowEntry.COLUMN_TOTALSEASONS)));

        String showtype = data.getString(data.getColumnIndex(ShowEntry.COLUMN_TYPE));
        if (ShowEntry.KEY_MOVIE.equals(showtype)){
            showEntity.setType(ShowEntity.TYPE.MOVIE);
        } else if (ShowEntry.KEY_SERIES.equals(showtype)){
            showEntity.setType(ShowEntity.TYPE.SERIES);
        } else {
            showEntity.setType(ShowEntity.TYPE.NONE);
        }


        byte[] cImage = data.getBlob(data.getColumnIndex(MyTVDbContract.ShowEntry.COLUMN_IMAGE));
        showEntity.setImage(BitmapFactory.decodeByteArray(cImage, 0, cImage.length));

        data.close();
        return showEntity;
    }


    @Override
    public ArrayList<ShowEntity> convertAllToDomainModel(Cursor data) {

        ArrayList<ShowEntity> showEntityArray = new ArrayList<>();

        if (data == null){
            return showEntityArray;
        }

        // Cursor is empty
        if (!(data.moveToFirst()) || data.getCount() == 0){
            return showEntityArray;
        }

        if (data.moveToFirst()) {
            do {
                ShowEntity showEntity = new ShowEntity();

                showEntity.setTitle(data.getString(data.getColumnIndex(ShowEntry.COLUMN_TITLE)));
                showEntity.setYear(data.getString(data.getColumnIndex(ShowEntry.COLUMN_YEAR)));
                showEntity.setRated(data.getString(data.getColumnIndex(ShowEntry.COLUMN_RATED)));
                showEntity.setReleased(data.getString(data.getColumnIndex(ShowEntry.COLUMN_RELEASED)));
                showEntity.setRuntime(data.getString(data.getColumnIndex(ShowEntry.COLUMN_RUNTIME)));
                showEntity.setGenre(data.getString(data.getColumnIndex(ShowEntry.COLUMN_GENRE)));
                showEntity.setDirector(data.getString(data.getColumnIndex(ShowEntry.COLUMN_DIRECTOR)));
                showEntity.setWriter(data.getString(data.getColumnIndex(ShowEntry.COLUMN_WRITER)));
                showEntity.setActors(data.getString(data.getColumnIndex(ShowEntry.COLUMN_ACTORS)));
                showEntity.setPlot(data.getString(data.getColumnIndex(ShowEntry.COLUMN_PLOT)));
                showEntity.setLanguage(data.getString(data.getColumnIndex(ShowEntry.COLUMN_LANGUAGE)));
                showEntity.setCountry(data.getString(data.getColumnIndex(ShowEntry.COLUMN_COUNTRY)));
                showEntity.setAwards(data.getString(data.getColumnIndex(ShowEntry.COLUMN_AWARDS)));
                showEntity.setPoster(data.getString(data.getColumnIndex(ShowEntry.COLUMN_POSTER)));
                showEntity.setMetascore(data.getString(data.getColumnIndex(ShowEntry.COLUMN_METASCORE)));
                showEntity.setImdbRating(data.getString(data.getColumnIndex(ShowEntry.COLUMN_IMDBRATING)));
                showEntity.setImdbVotes(data.getString(data.getColumnIndex(ShowEntry.COLUMN_IMDBVOTES)));
                showEntity.setImdbID(data.getString(data.getColumnIndex(ShowEntry.COLUMN_IMDBID)));
                showEntity.setTotalSeasons(data.getString(data.getColumnIndex(ShowEntry.COLUMN_TOTALSEASONS)));

                String showtype = data.getString(data.getColumnIndex(ShowEntry.COLUMN_TYPE));
                if (ShowEntry.KEY_MOVIE.equals(showtype)){
                    showEntity.setType(ShowEntity.TYPE.MOVIE);
                } else if (ShowEntry.KEY_SERIES.equals(showtype)){
                    showEntity.setType(ShowEntity.TYPE.SERIES);
                } else {
                    showEntity.setType(ShowEntity.TYPE.NONE);
                }

                byte[] cImage = data.getBlob(data.getColumnIndex(MyTVDbContract.ShowEntry.COLUMN_IMAGE));
                showEntity.setImage(BitmapFactory.decodeByteArray(cImage, 0, cImage.length));

                showEntityArray.add(showEntity);

            } while (data.moveToNext());
        }

        data.close();
        return showEntityArray;
    }
}
