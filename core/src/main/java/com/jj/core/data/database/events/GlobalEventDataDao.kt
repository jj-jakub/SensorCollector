package com.jj.core.data.database.events

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GlobalEventDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(event: GlobalEventEntity)

    @Query("SELECT * FROM globalevententity")
    fun getGlobalEvents(): Flow<List<GlobalEventEntity>>

    @Query("DELETE FROM globalevententity")
    suspend fun deleteAllFromTable()
}