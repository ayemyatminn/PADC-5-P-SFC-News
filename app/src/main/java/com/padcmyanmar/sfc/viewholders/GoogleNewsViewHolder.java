package com.padcmyanmar.sfc.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.padcmyanmar.sfc.R;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.delegates.NewsItemDelegate;

import butterknife.BindView;

/**
 * Created by PC on 6/23/2018.
 */

public class GoogleNewsViewHolder extends BaseViewHolder<NewsVO> {

    @BindView(R.id.tv_google_publication_title)
    TextView tvGooglePublicationTitle;

    @BindView(R.id.tv_google_brief)
    TextView tvGoogleBreif;

    @BindView(R.id.tv_google_news_posted_Date)
    TextView tvGoogleNewsPostedDate;

    @BindView(R.id.iv_google_news_hero_image)
    ImageView ivGoogleNewsHeroImage;

    private NewsItemDelegate mDelegate;

    private NewsVO mData;

    public GoogleNewsViewHolder(View itemView, NewsItemDelegate newsItemDelegate) {
        super(itemView);
        mDelegate=newsItemDelegate;
    }

    @Override
    public void setData(NewsVO data) {
        mData=data;

        tvGooglePublicationTitle.setText(data.getPublication().getTitle());
        tvGoogleBreif.setText(data.getBrief());
        tvGoogleNewsPostedDate.setText(data.getPostedDate());
        if (!data.getImages().isEmpty()) {
            Glide.with(ivGoogleNewsHeroImage).load(data.getImages().get(0)).into(ivGoogleNewsHeroImage);
        }else {
            ivGoogleNewsHeroImage.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        mDelegate.onTapNews(mData);

    }
}
