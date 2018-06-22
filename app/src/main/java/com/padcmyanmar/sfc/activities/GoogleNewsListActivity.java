package com.padcmyanmar.sfc.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.adapters.GoogleNewsAdapter;
import com.padcmyanmar.sfc.components.SmartRecyclerView;
import com.padcmyanmar.sfc.components.SmartScrollListener;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.mvp.presenters.NewsListPresenter;
import com.padcmyanmar.sfc.mvp.views.NewsListView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by PC on 6/23/2018.
 */

public class GoogleNewsListActivity extends BaseActivity implements NewsListView{

    @BindView(R.id.rv_google_news)
    SmartRecyclerView rvGoogleNews;

    private GoogleNewsAdapter mGoogleAdapter;

    private NewsListPresenter mPresenter;

    private SmartScrollListener mSmartScrollListener;

    private PublishSubject<List<NewsVO>> mNewsSubject;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_news_list);
        ButterKnife.bind(this,this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter=new NewsListPresenter(this);
        mPresenter.onCreate();

        rvGoogleNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false));
        mGoogleAdapter=new GoogleNewsAdapter(getApplicationContext(),mPresenter);
        rvGoogleNews.setAdapter(mGoogleAdapter);

        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {
                //Snackbar.make(rvNews, "This is all the data for NOW.", Snackbar.LENGTH_LONG).show();
                //TODO load more data.
            }
        });
        rvGoogleNews.addOnScrollListener(mSmartScrollListener);

        mNewsSubject=PublishSubject.create();
        mNewsSubject.subscribe(new io.reactivex.Observer<List<NewsVO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<NewsVO> newsVOS) {
                Log.d(SFCNewsApp.LOG_TAG, "onNext: " + newsVOS.size());
                mGoogleAdapter.appendNewData(newsVOS);


            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
        mPresenter.onFinishUIComponent(mNewsSubject);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mPresenter.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mPresenter.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        Snackbar.make(rvGoogleNews, errorMessage, Snackbar.LENGTH_INDEFINITE).show();
    }

    @Override
    public void displayNewsList(List<NewsVO> newsList) {
        mGoogleAdapter.appendNewData(newsList);
    }

    @Override
    public void lunchNewsDetailsScreen(String newsId) {
        Intent intent = NewsDetailsActivity.newIntent(getApplicationContext(),newsId);
        startActivity(intent);
    }
}
