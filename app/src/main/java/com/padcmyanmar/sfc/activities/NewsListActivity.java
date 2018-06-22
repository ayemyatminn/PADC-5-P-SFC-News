package com.padcmyanmar.sfc.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.SFCNewsApp;
import com.padcmyanmar.sfc.adapters.NewsAdapter;
import com.padcmyanmar.sfc.components.EmptyViewPod;
import com.padcmyanmar.sfc.components.SmartRecyclerView;
import com.padcmyanmar.sfc.components.SmartScrollListener;
import com.padcmyanmar.sfc.data.models.NewsModel;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.delegates.NewsItemDelegate;
import com.padcmyanmar.sfc.events.RestApiEvents;
import com.padcmyanmar.sfc.events.TapNewsEvent;
import com.padcmyanmar.sfc.mvp.presenters.NewsListPresenter;
import com.padcmyanmar.sfc.mvp.views.NewsListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class NewsListActivity extends BaseActivity
        implements NewsListView {

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.rv_news)
    SmartRecyclerView rvNews;

//    @BindView(R.id.vp_empty_news)
//    EmptyViewPod vpEmptyNews;

    private SmartScrollListener mSmartScrollListener;

    private NewsAdapter mNewsAdapter;

    private PublishSubject<List<NewsVO>> mNewsSubject;

    private NewsListPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);
        ButterKnife.bind(this, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPresenter=new NewsListPresenter(this);
        mPresenter.onCreate();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                        */

                //drawerLayout.openDrawer(GravityCompat.START);

                /*
                Intent intent = LoginRegisterActivity.newIntent(getApplicationContext());
                startActivity(intent);
                */

                Date today = new Date();
                Log.d(SFCNewsApp.LOG_TAG, "Today (with default format) : " + today.toString());
            }
        });

        //rvNews.setEmptyView(vpEmptyNews);

//       mNewsmodel= ViewModelProviders.of(this).get(NewsModel.class);
//       mNewsmodel.initDatabase(this);
//        mNewsmodel.getNews().observe(this, new Observer<List<NewsVO>>() {
//            @Override
//            public void onChanged(@Nullable List<NewsVO> newsVOS) {
//                mNewsAdapter.setNewData(newsVOS);
//            }
//        });

        

        mNewsSubject=PublishSubject.create();
        mNewsSubject.subscribe(new io.reactivex.Observer<List<NewsVO>>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(List<NewsVO> newsVOS) {
                Log.d(SFCNewsApp.LOG_TAG, "onNext: " + newsVOS.size());
                mNewsAdapter.appendNewData(newsVOS);

                processPrimeSingle();
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        mPresenter.onFinishUIComponent(mNewsSubject);

        rvNews.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        mNewsAdapter = new NewsAdapter(getApplicationContext(),mPresenter);
        rvNews.setAdapter(mNewsAdapter);



        mSmartScrollListener = new SmartScrollListener(new SmartScrollListener.OnSmartScrollListener() {
            @Override
            public void onListEndReach() {
                //Snackbar.make(rvNews, "This is all the data for NOW.", Snackbar.LENGTH_LONG).show();
                //TODO load more data.
            }
        });

        rvNews.addOnScrollListener(mSmartScrollListener);




    }

    private void processPrimeSingle(){
        Single<String> prime=Single.fromCallable(new Callable<String>() {
            @Override
            public String call() throws Exception {
                int number[]=new int[]{1,2,3,4,5,6,7,8,9,11,12,13};
                return calculatePrimeNumber(number);
            }
        });

        prime
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(String s) {
                        Toast.makeText(NewsListActivity.this, "Prime number are : " + s, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private String calculatePrimeNumber(int...numbers){
        String primenumber="";
        for (int i=0;i<numbers.length;i++){
            if (numbers[i]==2 || isPrime(numbers[i])){
            primenumber=primenumber + numbers[i] + ",";}
        }

        if (!TextUtils.isEmpty(primenumber) && primenumber.contains(",")){
            primenumber=primenumber.substring(0,primenumber.lastIndexOf(","));
        }

        return primenumber;
    }

    private boolean isPrime(int number){
        for (int i=2;i<number;i++){
            if (number % i ==0){
                return false;
            }
            else if (number % 3 ==0){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
    public void displayNewsList(List<NewsVO> newsList) {
        mNewsAdapter.appendNewData(newsList);
    }



    @Override
    public void lunchNewsDetailsScreen(String newsId) {
        Intent intent = NewsDetailsActivity.newIntent(getApplicationContext(),newsId);
        startActivity(intent);
    }

    @Override
    public void displayErrorMessage(String errorMessage) {
        Snackbar.make(rvNews, errorMessage, Snackbar.LENGTH_INDEFINITE).show();
    }
}
