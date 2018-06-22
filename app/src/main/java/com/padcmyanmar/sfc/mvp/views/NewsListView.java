package com.padcmyanmar.sfc.mvp.views;

import com.padcmyanmar.sfc.data.vo.NewsVO;

import java.util.List;

/**
 * Created by PC on 6/22/2018.
 */

public interface NewsListView {

    void displayNewsList(List<NewsVO> newsList);

    void displayErrorMessage(String errormsg);

    void lunchNewsDetailsScreen(String newsId);
}
