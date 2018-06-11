package com.padcmyanmar.sfc.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.ActedUserVO;

/**
 * Created by PC on 6/9/2018.
 */

@Dao
public interface ActedUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insetUser(ActedUserVO...actedUserVOS);

    @Query("DELETE FROM Acted_Users")
    void deleteAll();
}
