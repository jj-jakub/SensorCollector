package com.jj.core.data.travel.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [TravelItemEntity::class],
    version = 1
)
abstract class TravelDatabase : RoomDatabase() {

    abstract val travelItemDataDao: TravelItemDataDao
}