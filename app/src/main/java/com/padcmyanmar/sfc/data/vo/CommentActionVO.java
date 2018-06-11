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

@Entity(tableName = "Comment_Action",foreignKeys = {@ForeignKey(entity = NewsVO.class,parentColumns = "News_ID",childColumns = "News_Id"),
        @ForeignKey(entity = ActedUserVO.class,parentColumns = "User_ID",childColumns = "Acted_User_Id")})
public class CommentActionVO {

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "Comment_ID")
    @SerializedName("comment-id")
    private String commentId;

    @ColumnInfo(name = "Comment")
    @SerializedName("comment")
    private String comment;

    @ColumnInfo(name = "Comment_Date")
    @SerializedName("comment-date")
    private String commentDate;

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

    @ColumnInfo(name = "News_Id")
    private String newsId;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getCommentId() {
        return commentId;
    }

    public String getComment() {
        return comment;
    }

    public String getCommentDate() {
        return commentDate;
    }

    public ActedUserVO getActedUser() {
        return actedUser;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public void setActedUser(ActedUserVO actedUser) {
        this.actedUser = actedUser;
    }
}
