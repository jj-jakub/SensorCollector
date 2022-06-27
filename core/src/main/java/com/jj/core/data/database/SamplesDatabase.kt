package com.jj.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fluffycat.sensorsmanager.data.AccelerationDataEntity
import com.jj.core.data.database.events.GlobalEventDataDao
import com.jj.core.data.database.events.GlobalEventEntity
import com.jj.core.data.database.samples.accelerometer.AccelerationDataDao
import com.jj.core.data.database.samples.gps.GPSDataDao
import com.jj.core.data.database.samples.gps.GPSDataEntity

@Database(
    entities = [GPSDataEntity::class, GlobalEventEntity::class, AccelerationDataEntity::class],
    version = 1
)
abstract class SamplesDatabase : RoomDatabase() {

    abstract val gpsDataDao: GPSDataDao
    abstract val globalEventDataDao: GlobalEventDataDao
    abstract val accelerationDataDao: AccelerationDataDao
}