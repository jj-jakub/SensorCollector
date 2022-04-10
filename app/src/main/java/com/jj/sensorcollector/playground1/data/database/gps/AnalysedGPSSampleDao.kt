package com.jj.sensorcollector.playground1.data.database.gps

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AnalysedGPSSampleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(analysedGPSSampleEntity: AnalysedGPSSampleEntity)

    @Query("SELECT * FROM AnalysedGPSSampleEntity")
    fun getAnalysedGPSSampleEntities(): Flow<List<AnalysedGPSSampleEntity>>

    @Query("SELECT * FROM AnalysedGPSSampleEntity WHERE id=(SELECT max(id) FROM AnalysedGPSSampleEntity)")
    fun getLatestAnalysedGPSSampleEntity(): Flow<AnalysedGPSSampleEntity>
}