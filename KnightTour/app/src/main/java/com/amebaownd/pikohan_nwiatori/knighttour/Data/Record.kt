package com.amebaownd.pikohan_nwiatori.knighttour.Data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Record{
    @PrimaryKey
    var stageId:Int=0

    var time:Int=9999

    var rank:String=""
}