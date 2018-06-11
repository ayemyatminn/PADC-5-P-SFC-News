package com.padcmyanmar.sfc.data.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.CommentActionVO;

/**
 * Created by PC on 6/9/2018.
 */

@Dao
public interface CommentDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertComment(CommentActionVO...commentActionVO);

    @Query("DELETE FROM Comment_Action")
    void deleteAll();
}
