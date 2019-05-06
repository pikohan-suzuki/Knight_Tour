package com.amebaownd.pikohan_nwiatori.knighttour

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase

@Database(entities = arrayOf(Stage::class,StageInfo::class,Record::class),version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun stageDao(): StageDao
    abstract fun stageInfoDao(): StageInfoDao
    abstract fun recordDao(): RecordDao
}