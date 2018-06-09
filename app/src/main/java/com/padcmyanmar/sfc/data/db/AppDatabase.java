package com.padcmyanmar.sfc.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.padcmyanmar.sfc.data.vo.ActedUserVO;
import com.padcmyanmar.sfc.data.vo.CommentActionVO;
import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;
import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;
import com.padcmyanmar.sfc.data.vo.SentToVO;

/**
 * Created by PC on 6/9/2018.
 */

@Database(entities = {NewsVO.class, PublicationVO.class, ActedUserVO.class, CommentActionVO.class, FavoriteActionVO.class, SentToVO.class},version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DB_NAME="PADC_SFC_NEWS.DB";

    private static AppDatabase INSTANCE;

    public abstract NewsDao newsDao();

    public static AppDatabase getInMemoryDatabase(Context context){
        if (INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DB_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE=null;
    }
}
