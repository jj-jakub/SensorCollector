package com.jj.core.data.database.gps.analysis.path

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GPSPathDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(GPSPathDataEntity: GPSPathDataEntity): Long

    @Query("SELECT * FROM GPSPathDataEntity WHERE id=:pathId")
    fun getGPSPathDataEntityForPathId(pathId: Int): GPSPathDataEntity?

    @Query("SELECT * FROM GPSPathDataEntity")
    fun getGPSPathDataEntities(): Flow<List<GPSPathDataEntity>>

    @Query("SELECT * FROM GPSPathDataEntity WHERE id=(SELECT max(id) FROM GPSPathDataEntity)")
    fun getLatestGPSPathDataEntity(): Flow<GPSPathDataEntity>
}