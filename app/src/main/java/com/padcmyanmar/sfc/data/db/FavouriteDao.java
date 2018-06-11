package com.padcmyanmar.sfc.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.FavoriteActionVO;

/**
 * Created by PC on 6/9/2018.
 */

@Dao
public interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertFavourite(FavoriteActionVO...favoriteActionVOS);

    @Query("DELETE FROM Favourite_Action")
    void deleteAll();
}
