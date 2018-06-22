package com.padcmyanmar.sfc.mvp.presenters;

import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.delegates.NewsItemDelegate;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.mvp.views.NewsListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import io.reactivex.subjects.PublishSubject;

/**
 * Created by PC on 6/22/2018.
 */

public class NewsListPresenter extends BasePresenter<NewsListView> implements NewsItemDelegate{

    private NewsListView mViews;

    public NewsListPresenter(NewsListView mView) {
        super(mView);
        mViews=mView;
    }


    public void onFinishUIComponent(PublishSubject<List<NewsVO>> mNewsSubject){
        NewsModel.getInstance().startLoadingMMNews(mNewsSubject);
    }

    public void onStart(){
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }

    }

    public void onStop(){
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event){
        if (event.getLoadNews()==null){
            mViews.displayErrorMessage("Empty Data");
        }
        else {
            mViews.displayNewsList(event.getLoadNews());
        }

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onErrorInvokinApi(RestApiEvents.ErrorInvokingAPIEvent event){
        mViews.displayErrorMessage(event.getErrorMsg());
    }

    @Override
    public void onTapComment() {

    }

    @Override
    public void onTapSendTo() {

    }

    @Override
    public void onTapFavorite() {

    }

    @Override
    public void onTapStatistics() {

    }

    @Override
    public void onTapNews(NewsVO news) {
        mViews.lunchNewsDetailsScreen(news.getNewsId());
    }
}
