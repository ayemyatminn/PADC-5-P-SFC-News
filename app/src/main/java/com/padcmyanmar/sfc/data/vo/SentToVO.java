package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Created by aung on 12/3/17.
 */

@Entity(tableName = "SentTo_Action")
public class SentToVO {

    @NotNull
    @PrimaryKey
    @SerializedName("send-to-id")
    private String sendToId;

    @SerializedName("sent-date")
    private String sentDate;

    @Ignore
    @SerializedName("acted-user")
    private ActedUserVO sender;

    @Ignore
    @SerializedName("received-user")
    private ActedUserVO receiver;

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
