package com.padcmyanmar.sfc.mvp.presenters;

import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.mvp.views.NewsDetailsView;

/**
 * Created by PC on 6/22/2018.
 */

public class NewsDetailPresenter {

    private NewsDetailsView mView;

    public NewsDetailPresenter(NewsDetailsView view) {
        this.mView = view;
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

    public void onFinishUIComponent(String newsID){
        NewsVO newsVO=NewsModel.getInstance().getNewsByID(newsID);
        mView.displayUIComponent(newsVO);
    }
}
