package com.amebaownd.pikohan_nwiatori.knighttour.Data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(primaryKeys = arrayOf("stageId","row","column"))
class StageInfo{

    var stageId:Int=0

    var row:Int=1

    var column:Int=1
}