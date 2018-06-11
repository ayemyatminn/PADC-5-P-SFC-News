package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Created by aung on 12/3/17.
 */

@Entity(tableName = "Favourite_Action",foreignKeys = {@ForeignKey(entity = NewsVO.class,parentColumns = "News_ID",childColumns = "News_Id"),
                                                        @ForeignKey(entity = ActedUserVO.class,parentColumns = "User_ID",childColumns = "Acted_User_Id")})
public class FavoriteActionVO {

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "Favourite_ID")
    @SerializedName("favorite-id")
    private String favoriteId;

    @ColumnInfo(name = "Favourite_Date")
    @SerializedName("favorite-date")
    private String favoriteDate;

    @ColumnInfo(name = "News_Id")
    private String newsId;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    @Ignore
    @SerializedName("acted-user")
    private ActedUserVO actedUser;

    @ColumnInfo(name = "Acted_User_Id")
    private String actedUserId;

    public String getActedUserId() {
        if (actedUser!=null){
            return actedUser.getUserId();
        }
        return null;
    }

    public void setActedUserId(String actedUserId) {
        this.actedUserId = actedUserId;
    }

    public String getFavoriteId() {
        return favoriteId;
    }

    public String getFavoriteDate() {
        return favoriteDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public void setFavoriteId(String favoriteId) {
        this.favoriteId = favoriteId;
    }

    public void setFavoriteDate(String favoriteDate) {
        this.favoriteDate = favoriteDate;
    }

    public void setActedUser(ActedUserVO actedUser) {
        this.actedUser = actedUser;
    }
}
