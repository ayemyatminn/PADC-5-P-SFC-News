package com.padcmyanmar.sfc.network;

import com.padcmyanmar.sfc.network.reponses.GetNewsResponse;
import com.padcmyanmar.sfc.utils.AppConstants;

import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by aung on 12/3/17.
 */

public interface MMNewsAPI {

    @FormUrlEncoded
    @POST("v1/getMMNews.php")
    Single<GetNewsResponse> loadMMNews(
            @Field("page") int pageIndex,
            @Field("access_token") String accessToken);


    @GET(AppConstants.API_GET_NEWS_V2)
    Observable<GetNewsResponse> getMMNewsList();


//    @FormUrlEncoded
//    @POST("v1/getMMNews.php")
//    Call<GetNewsResponse> loadMMNews(
//            @Field("page") int pageIndex,
//            @Field("access_token") String accessToken);
}
