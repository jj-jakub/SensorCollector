package com.jj.core.data.travel.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fluffycat.sensorsmanager.data.AccelerationDataEntity
import com.jj.core.data.database.events.GlobalEventDataDao
import com.jj.core.data.database.events.GlobalEventEntity
import com.jj.core.data.database.samples.accelerometer.AccelerationDataDao
import com.jj.core.data.database.samples.gps.GPSDataDao
import com.jj.core.data.database.samples.gps.GPSDataEntity
import com.jj.core.data.travel.items.TravelItemDataDao
import com.jj.core.data.travel.items.TravelItemEntity

@Database(
    entities = [TravelItemEntity::class],
    version = 1
)
abstract class TravelDatabase : RoomDatabase() {

    abstract val travelItemDataDao: TravelItemDataDao
}