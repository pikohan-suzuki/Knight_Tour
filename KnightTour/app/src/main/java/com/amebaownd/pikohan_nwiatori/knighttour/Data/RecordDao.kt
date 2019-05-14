package com.amebaownd.pikohan_nwiatori.knighttour.Data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface  RecordDao{
    @Query("SELECT * FROM record ORDER BY stageId")
    fun getAllOrderByStageId():LiveData<List<Record>>

    @Query("SELECT * FROM record WHERE stageId = (:stageId)")
    fun getByStageId(stageId:Int):LiveData<Record>

    @Insert
    fun insertAll(vararg record: Record)
}