package com.jj.sensorcollector.data.database.samples

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GPSDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(gpsDataEntity: GPSDataEntity)

    @Query("SELECT * FROM gpsdataentity")
    fun getGPSSamples(): Flow<List<GPSDataEntity>>

    @Query("DELETE FROM gpsdataentity")
    suspend fun deleteAllFromTable()
}