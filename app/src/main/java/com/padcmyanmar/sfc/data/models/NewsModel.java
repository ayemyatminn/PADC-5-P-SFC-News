package com.padcmyanmar.sfc.data.models;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.data.db.AppDatabase;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.network.MMNewsAPI;
import com.padcmyanmar.sfc.network.MMNewsDataAgent;
import com.padcmyanmar.sfc.network.MMNewsDataAgentImpl;
import com.padcmyanmar.sfc.network.reponses.GetNewsResponse;
import com.padcmyanmar.sfc.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import io.reactivex.Scheduler;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel{

    private static NewsModel objInstance;
    private AppDatabase mAppDatabase;

    private PublishSubject<List<NewsVO>> mMMNewsSubject;

    private Map<String,NewsVO> mNewsMap;
    private int mmNewsPageIndex = 1;

    private MMNewsAPI theNewsApi;

    public MMNewsAPI getTheNewsApi() {
        return theNewsApi;
    }

    private NewsModel() {
        EventBus.getDefault().register(this);
        initMMNewsApi();
//        startLoadingMMNews();
        mNewsMap=new HashMap<>();

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


//    public LiveData<List<NewsVO>> getNews(){
//        return mAppDatabase.newsDao().getAllNews();
//    }

    public void startLoadingMMNews(PublishSubject<List<NewsVO>> newsSubject) {

        this.mMMNewsSubject=newsSubject;

        //MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);

        Single<GetNewsResponse> getNewsResponseObservable=theNewsApi.loadMMNews(mmNewsPageIndex,AppConstants.ACCESS_TOKEN);

        getNewsResponseObservable
                .subscribeOn(Schedulers.io())
                .map(new Function<GetNewsResponse, List<NewsVO>>() {
                    @Override
                    public List<NewsVO> apply(GetNewsResponse getNewsResponse)  {
                        return getNewsResponse.getNewsList();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<NewsVO>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

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

    public NewsVO getNewsByID(String newsId){
       return mNewsMap.get(newsId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {

        for (NewsVO news:event.getLoadNews()){
            mNewsMap.put(news.getNewsId(),news);
        }

        //mNews.addAll(event.getLoadNews());
        mmNewsPageIndex = event.getLoadedPageIndex() + 1;

        for(NewsVO news :event.getLoadNews()){
            mAppDatabase.publicationDao().insertPubcation(news.getPublication());
            //Log.d(SFCNewsApp.LOG_TAG,"Total insert count:" + insertPublication);
            mAppDatabase.publicationDao().getAllPublication();
        }

//        long[] insertedIds = mAppDatabase.newsDao().insertNews(event.getLoadNews().toArray(new NewsVO[0]));
//        Log.d(SFCNewsApp.LOG_TAG, "Total inserted count : " + insertedIds.length);
    }

    private void initMMNewsApi() {

        final OkHttpClient okHttpClient=new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30,TimeUnit.SECONDS)
                .readTimeout(30,TimeUnit.SECONDS)
                .build();

        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(AppConstants.MMNEWS_NEWS_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(new Gson()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        theNewsApi=retrofit.create(MMNewsAPI.class);
    }
}
