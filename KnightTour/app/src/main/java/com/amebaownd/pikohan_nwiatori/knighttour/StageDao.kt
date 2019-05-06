package com.amebaownd.pikohan_nwiatori.knighttour

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query

@Dao
interface StageDao{

    @Query("SELECT * FROM stage ORDER BY stageId")
    fun getAll():LiveData<List<Stage>>

    @Query("SELECT * FROM stage WHERE stageId = (:stageId)")
    fun getByStageId(stageId:Int):LiveData<Stage>

    @Insert
    fun insertAll(vararg stage:Stage)


}