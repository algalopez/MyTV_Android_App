package com.algalopez.mytv.domain.presentation;

import com.algalopez.mytv.domain.model.AEntity;

import java.util.ArrayList;


/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    11/2/16
 */

public class ResponseModel<H extends AEntity, D extends AEntity> {

    public enum ActionType {SEARCH, FAVOURITE, SHOW, SEASON, EPISODE, ERROR, HISTORY}
    public enum RepositoryType {LOCAL, NETWORK, HISTORY, UNDEFINED}

    private H mHeader;
    private ArrayList<D> mData = new ArrayList<>();
    private ActionType mAction = ActionType.ERROR;
    private RepositoryType mRepository = RepositoryType.UNDEFINED;

    public H getHeader(){ return mHeader; }
    public ArrayList<D> getData() { return mData; }
    public ActionType getAction() { return mAction; }
    public RepositoryType getRepository() { return mRepository; }

    public void setHeader(H header){ this.mHeader = header;}
    public void appendData(D data) { this.mData.add(data); }
    public void setAction(ActionType T) { this.mAction = T; }
    public void setRepository(RepositoryType T) { this.mRepository = T; }
}
