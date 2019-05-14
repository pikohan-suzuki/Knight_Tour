package com.amebaownd.pikohan_nwiatori.knighttour.Data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface StageInfoDao{
    @Query("SELECT * FROM StageInfo ORDER BY stageId,`row`,`column`")
    fun getAll():LiveData<List<StageInfo>>

    @Query("SELECT * FROM StageInfo WHERE stageId=(:stageId) ORDER BY `row`,`column`")
    fun getByStageId(stageId:Int):LiveData<List<StageInfo>>

    @Insert
    fun insertAll(vararg stageInfo: StageInfo)
}