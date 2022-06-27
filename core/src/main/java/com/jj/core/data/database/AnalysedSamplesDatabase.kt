package com.jj.core.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.jj.core.data.database.accelerometer.AnalysedAccelerometerSampleDao
import com.jj.core.data.database.accelerometer.AnalysedAccelerometerSampleEntity
import com.jj.core.data.database.gps.AnalysedGPSSampleDao
import com.jj.core.data.database.gps.AnalysedGPSSampleEntity

@Database(
    entities = [
        AnalysedAccelerometerSampleEntity::class,
        AnalysedGPSSampleEntity::class
    ],
    version = 1
)
@TypeConverters(AnalysisResultTypeConverter::class)
abstract class AnalysedSamplesDatabase : RoomDatabase() {

    abstract val analysedAccelerometerSampleDao: AnalysedAccelerometerSampleDao
    abstract val analysedGPSSampleDao: AnalysedGPSSampleDao
}