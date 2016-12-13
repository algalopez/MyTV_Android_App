package com.algalopez.mytv.data.local.converter;

import android.content.ContentValues;
import android.database.Cursor;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */

public interface IConverter<DomainModel extends com.algalopez.mytv.domain.model.AEntity> {

    ContentValues convertToDataModel(DomainModel data);

    DomainModel convertToDomainModel(Cursor data);

    ArrayList<DomainModel> convertAllToDomainModel(Cursor data);
}
