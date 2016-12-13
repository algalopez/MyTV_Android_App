package com.algalopez.mytv.domain.scheduler;

import com.algalopez.mytv.domain.interactor.IInteractor;

/**
 * AUTHOR:  Alvaro Garcia Lopez (algalopez)
 * DATE:    10/26/16
 */

public interface IExecutor<O extends IInteractor> {


    void execute (O interactor);
}
