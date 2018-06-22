package com.padcmyanmar.sfc.mvp.presenters;

import com.padcmyanmar.sfc.mvp.views.BaseView;

/**
 * Created by PC on 6/23/2018.
 */

public abstract class BasePresenter<T extends BaseView> {

    protected T mView;

    public BasePresenter(T mView) {
        this.mView = mView;
    }

    public void onCreate(){

    }

    public void onStart(){

    }

    public void onResume(){

    }

    public void onPause(){

    }

    public void onStop(){

    }

    public void onDestroy(){

    }
}
