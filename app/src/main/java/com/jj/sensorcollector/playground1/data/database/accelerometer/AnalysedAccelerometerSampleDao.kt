package com.jj.sensorcollector.playground1.data.database.accelerometer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysedAccelerometerSampleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(analysedAccelerometerSampleEntity: AnalysedAccelerometerSampleEntity)

    @Query("SELECT * FROM AnalysedAccelerometerSampleEntity")
    fun getAnalysedAccelerationSampleEntities(): Flow<List<AnalysedAccelerometerSampleEntity>>

    @Query("SELECT * FROM AnalysedAccelerometerSampleEntity ORDER BY sampleTime DESC LIMIT 1")
    fun getLatestAnalysedAccelerationSampleEntity(): Flow<AnalysedAccelerometerSampleEntity>

    @Query("DELETE FROM AnalysedAccelerometerSampleEntity")
    suspend fun deleteAllFromTable()
}