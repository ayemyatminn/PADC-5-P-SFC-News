package com.padcmyanmar.sfc.data.vo;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;

/**
 * Created by aung on 12/3/17.
 */

@Entity(tableName = "Publication")
public class PublicationVO {

    @NotNull
    @PrimaryKey
    @SerializedName("publication_id")
    @ColumnInfo(name = "Publication_ID")
    private String publicationId;

    @ColumnInfo(name = "Title")
    @SerializedName("title")
    private String title;

    @ColumnInfo(name = "Logo")
    @SerializedName("logo")
    private String logo;

    public String getPublicationId() {
        return publicationId;
    }

    public String getTitle() {
        return title;
    }

    public String getLogo() {
        return logo;
    }

    public void setPublicationId(String publicationId) {
        this.publicationId = publicationId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
