package com.amebaownd.pikohan_nwiatori.knighttour

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Stage{
    @PrimaryKey
    var stageId:Int=0

    var row:Int=1

    var column:Int=1

    var sTime:Int=0

    var aTime:Int=0

    var bTime:Int=0

    var cTime:Int=0
}