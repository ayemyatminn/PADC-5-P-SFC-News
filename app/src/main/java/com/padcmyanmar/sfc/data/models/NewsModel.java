package com.padcmyanmar.sfc.data.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.data.db.AppDatabase;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.network.MMNewsDataAgent;
import com.padcmyanmar.sfc.network.MMNewsDataAgentImpl;
import com.padcmyanmar.sfc.network.reponses.GetNewsResponse;
import com.padcmyanmar.sfc.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel {

    private static NewsModel objInstance;
    private AppDatabase mAppDatabase;

    private PublishSubject<List<NewsVO>> mMMNewsSubject;

    private List<NewsVO> mNews;
    private int mmNewsPageIndex = 1;

    public NewsModel() {
        EventBus.getDefault().register(this);
//        startLoadingMMNews();


    }

    public static NewsModel getInstance() {
        if(objInstance == null) {
            objInstance = new NewsModel();
        }
        return objInstance;
    }

    public void initDatabase(Context context){
        mAppDatabase=AppDatabase.getInMemoryDatabase(context);
    }

    public void initPublishSubject(PublishSubject<List<NewsVO>> newsSubject){
        this.mMMNewsSubject=newsSubject;
    }

//    public LiveData<List<NewsVO>> getNews(){
//        return mAppDatabase.newsDao().getAllNews();
//    }

    public void startLoadingMMNews() {
        //MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);

        Single<GetNewsResponse> getNewsResponseObservable=getMMNews();

        getNewsResponseObservable
                .subscribeOn(Schedulers.io())
                .map(new Function<GetNewsResponse, List<NewsVO>>() {
                    @Override
                    public List<NewsVO> apply(GetNewsResponse getNewsResponse)  {
                        return getNewsResponse.getNewsList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<NewsVO>>() {
                    @Override
                   public void onSuccess(List<NewsVO> newsVOs) {
                        Log.d(SFCNewsApp.LOG_TAG, "onSuccess: " + newsVOs.size());
                        mMMNewsSubject.onNext(newsVOs);
                    }

                    @Override
                   public void onError(Throwable e) {
                        Log.d(SFCNewsApp.LOG_TAG, "onError: " + e.getMessage());
                    }
               });
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {
        mNews.addAll(event.getLoadNews());
        mmNewsPageIndex = event.getLoadedPageIndex() + 1;

        for(NewsVO news :event.getLoadNews()){
            mAppDatabase.publicationDao().insertPubcation(news.getPublication());
            //Log.d(SFCNewsApp.LOG_TAG,"Total insert count:" + insertPublication);
            mAppDatabase.publicationDao().getAllPublication();
        }

//        long[] insertedIds = mAppDatabase.newsDao().insertNews(event.getLoadNews().toArray(new NewsVO[0]));
//        Log.d(SFCNewsApp.LOG_TAG, "Total inserted count : " + insertedIds.length);



    }

    public Single<GetNewsResponse> getMMNews(){
        SFCNewsApp rxjava=new SFCNewsApp();
        return rxjava.getTheNewsApi().loadMMNews(mmNewsPageIndex,AppConstants.ACCESS_TOKEN);
    }


}
