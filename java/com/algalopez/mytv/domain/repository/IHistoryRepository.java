package com.algalopez.mytv.domain.repository;

import android.net.Uri;

import com.algalopez.mytv.domain.model.SearchEntity;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/7/16
 */

public interface IHistoryRepository {


    ArrayList<SearchEntity> getHistory();
    long setHistory(SearchEntity data);
    long removeHistory();



}
