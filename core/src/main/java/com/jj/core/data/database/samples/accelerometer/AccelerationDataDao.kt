package com.jj.core.data.database.samples.accelerometer

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AccelerationDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(accelerationDataEntity: AccelerationDataEntity)

    @Query("SELECT * FROM accelerationdataentity")
    fun getAccelerationSamples(): Flow<List<AccelerationDataEntity>>

    @Query("DELETE FROM accelerationdataentity")
    suspend fun deleteAllFromTable()
}