package com.algalopez.mytv.data.omdb.model;

import com.algalopez.mytv.data.omdb.network.OMDBContract;

import java.util.ArrayList;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/27/16
 */


public abstract class AEntity <H,D> {


    public static final int TYPE_SEARCH = 1;
    public static final int TYPE_SHOW = 2;
    public static final int TYPE_SEASON = 3;
    public static final int TYPE_EPISODE = 4;
    public static final int TYPE_ERROR = 5;

    private int mType = TYPE_ERROR;

    H mHeader;
    ArrayList<D> mData;


    /* TYPE */
    public void setType(int type) { mType = type; }
    public int getType() { return mType; }


    /* HEADER */
    public H getHeader() { return mHeader; }
    public void setHeader(H header){ mHeader = header; }


    /* DATA */
    public ArrayList<D> getData() { return mData; }
    public void appendData(D data){ mData.add(data); }


    public abstract H newHeader();
    public abstract D newData();

}
