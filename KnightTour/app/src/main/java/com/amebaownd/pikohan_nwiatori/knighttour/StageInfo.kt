package com.amebaownd.pikohan_nwiatori.knighttour

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class StageInfo{
    @PrimaryKey
    var stageId:Int=0
    @PrimaryKey
    var row:Int=1
    @PrimaryKey
    var column:Int=1
}