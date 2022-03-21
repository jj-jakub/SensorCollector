package com.jj.sensorcollector.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.jj.sensorcollector.data.database.events.GlobalEventEntity
import com.jj.sensorcollector.data.database.samples.GPSDataDao
import com.jj.sensorcollector.data.database.samples.GPSDataEntity

@Database(
        entities = [GPSDataEntity::class, GlobalEventEntity::class],
        version = 1
)
abstract class SamplesDatabase : RoomDatabase() {

    abstract val gpsDataDao: GPSDataDao
    abstract val globalEventDataDao: GPSDataDao
}