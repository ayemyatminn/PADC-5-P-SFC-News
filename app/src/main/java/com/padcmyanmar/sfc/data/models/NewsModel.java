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
import com.padcmyanmar.sfc.utils.AppConstants;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aung on 12/3/17.
 */

public class NewsModel {

    private static NewsModel objInstance;
    private AppDatabase mAppDatabase;

    private int mmNewsPageIndex = 1;

    private NewsModel() {
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

    public LiveData<List<NewsVO>> getNews(){
        return mAppDatabase.newsDao().getAllNews();
    }

    public void startLoadingMMNews() {
        MMNewsDataAgentImpl.getInstance().loadMMNews(AppConstants.ACCESS_TOKEN, mmNewsPageIndex);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNewsDataLoaded(RestApiEvents.NewsDataLoadedEvent event) {



        for(NewsVO news :event.getLoadNews()){
            mAppDatabase.publicationDao().insertPubcation(news.getPublication());
            //Log.d(SFCNewsApp.LOG_TAG,"Total insert count:" + insertPublication);
            mAppDatabase.publicationDao().getAllPublication();
        }

//        long[] insertedIds = mAppDatabase.newsDao().insertNews(event.getLoadNews().toArray(new NewsVO[0]));
//        Log.d(SFCNewsApp.LOG_TAG, "Total inserted count : " + insertedIds.length);



    }


}
