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

@Entity(tableName = "SentTo_Action",foreignKeys = {@ForeignKey(entity = NewsVO.class,parentColumns = "News_ID",childColumns ="News_Id" ),
                                                    @ForeignKey(entity = ActedUserVO.class,parentColumns = "User_ID",childColumns = "Sender_Id"),
                                                    @ForeignKey(entity = ActedUserVO.class,parentColumns = "User_ID",childColumns = "Receiver_Id")})
public class SentToVO {

    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "SendTo_ID")
    @SerializedName("send-to-id")
    private String sendToId;

    @ColumnInfo(name = "Send_Date")
    @SerializedName("sent-date")
    private String sentDate;

    @Ignore
    @SerializedName("acted-user")
    private ActedUserVO sender;

    @Ignore
    @SerializedName("received-user")
    private ActedUserVO receiver;

    @ColumnInfo(name = "Sender_Id")
    private String actedUserId;

    public String getActedUserId() {
        if (sender!=null){
            return sender.getUserId();
        }
        return null;
    }

    public void setActedUserId(String actedUserId) {
        this.actedUserId = actedUserId;
    }

    @ColumnInfo(name = "Receiver_Id")
    private String receivedUserId;

    public String getReceivedUserId() {
        if (receiver!=null){
            return receiver.getUserId();
        }
        return null;
    }

    public void setReceivedUserId(String receivedUserId) {
        this.receivedUserId = receivedUserId;
    }

    @ColumnInfo(name = "News_Id")
    private String newsId;

    public String getNewsId() {
        return newsId;
    }

    public void setNewsId(String newsId) {
        this.newsId = newsId;
    }

    public String getSendToId() {
        return sendToId;
    }

    public String getSentDate() {
        return sentDate;
    }

    public ActedUserVO getSender() {
        return sender;
    }

    public ActedUserVO getReceiver() {
        return receiver;
    }

    public void setSendToId(String sendToId) {
        this.sendToId = sendToId;
    }

    public void setSentDate(String sentDate) {
        this.sentDate = sentDate;
    }

    public void setSender(ActedUserVO sender) {
        this.sender = sender;
    }

    public void setReceiver(ActedUserVO receiver) {
        this.receiver = receiver;
    }
}
