package com.padcmyanmar.sfc.data.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.padcmyanmar.sfc.data.vo.NewsVO;
import com.padcmyanmar.sfc.data.vo.PublicationVO;

import java.util.List;

/**
 * Created by PC on 6/9/2018.
 */

@Dao
public interface PublicationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long[] insertPublication(PublicationVO...publicationVO);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insertPubcation(PublicationVO publicationVO);

    @Query("SELECT * FROM Publication")
    LiveData<List<PublicationVO>> getAllPublication();


    @Query("DELETE FROM Publication")
    void deleteAll();
}
