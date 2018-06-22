package com.padcmyanmar.sfc.mvp.presenters;

import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.mvp.views.NewsDetailsView;

/**
 * Created by PC on 6/22/2018.
 */

public class NewsDetailPresenter extends BasePresenter<NewsDetailsView>{

    private NewsDetailsView mViews;

    public NewsDetailPresenter(NewsDetailsView mView) {
        super(mView);
        mViews=mView;
    }

    public void onFinishUIComponent(String newsID){
        NewsVO newsVO=NewsModel.getInstance().getNewsByID(newsID);
        mView.displayUIComponent(newsVO);
    }
}
