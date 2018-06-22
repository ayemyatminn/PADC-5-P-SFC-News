package com.padcmyanmar.sfc.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.adapters.NewsImagesPagerAdapter;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.mvp.presenters.NewsDetailPresenter;
import com.padcmyanmar.sfc.mvp.views.NewsDetailsView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by aung on 11/11/17.
 */

public class NewsDetailsActivity extends BaseActivity implements NewsDetailsView {

    private static final String IE_NEWS_ID="newsId";

    private NewsDetailPresenter mPresenter;

    @BindView(R.id.vp_news_details_images)
    ViewPager vpNewsDetailsImages;


    public static Intent newIntent(Context context,String newsId) {
        Intent intent = new Intent(context, NewsDetailsActivity.class);
        intent.putExtra(IE_NEWS_ID,newsId);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        ButterKnife.bind(this, this);

        mPresenter=new NewsDetailPresenter(this);
        mPresenter.onCreate();

        NewsImagesPagerAdapter newsImagesPagerAdapter = new NewsImagesPagerAdapter(getApplicationContext());
        vpNewsDetailsImages.setAdapter(newsImagesPagerAdapter);

        String newsID= getIntent().getStringExtra(IE_NEWS_ID);
        mPresenter.onFinishUIComponent(newsID);
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
    public void displayUIComponent(NewsVO news) {
        TextView tvNewsDetails=findViewById(R.id.tv_news_details);
        tvNewsDetails.setText(news.getDetails());
    }

}
