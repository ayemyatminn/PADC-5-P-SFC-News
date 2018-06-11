package com.padcmyanmar.sfc.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.SentToVO;

/**
 * Created by PC on 6/9/2018.
 */

@Dao
public interface SentToDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertSendTo(SentToVO...sentToVOS);

    @Query("DELETE FROM SentTo_Action")
    void deleteAll();
}
