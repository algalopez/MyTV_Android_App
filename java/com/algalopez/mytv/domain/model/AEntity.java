package com.algalopez.mytv.domain.model;

import static com.algalopez.mytv.domain.model.AEntity.ActionType.ERROR;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/29/16
 */

public abstract class AEntity {

    public enum ActionType {FAVOURITE, SEARCH, SHOW, SEASON, EPISODE, HISTORY, ERROR};

    private ActionType mAction = ERROR;

    public void setAction(ActionType type) { mAction = type; }
    public ActionType getAction() { return mAction; }




}
