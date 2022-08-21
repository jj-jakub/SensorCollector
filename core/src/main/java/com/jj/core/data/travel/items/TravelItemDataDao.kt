package com.jj.core.data.travel.items

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TravelItemDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTravelItemEntity(travelItemEntity: TravelItemEntity)

    @Query("SELECT * FROM TravelItemEntity")
    fun getTravelItemEntities(): Flow<List<TravelItemEntity>>

    @Query("SELECT * FROM TravelItemEntity WHERE id=(SELECT max(id) FROM TravelItemEntity)")
    fun getLatestTravelItemEntity(): Flow<TravelItemEntity>

    @Query("DELETE FROM TravelItemEntity")
    suspend fun deleteAllTravelItemEntities()

    @Delete
    suspend fun deleteTravelItemEntity(travelItemEntity: TravelItemEntity)
}