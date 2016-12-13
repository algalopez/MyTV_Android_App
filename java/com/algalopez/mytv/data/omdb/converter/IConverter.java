package com.algalopez.mytv.data.omdb.converter;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */


public interface IConverter<DomainModel, DataModel> {

    DomainModel convertToDomainModel (DataModel omdb);

}
