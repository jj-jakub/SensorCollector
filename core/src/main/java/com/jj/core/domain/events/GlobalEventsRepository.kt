package com.jj.core.domain.events

import com.jj.core.data.sensors.GlobalEvent
import kotlinx.coroutines.flow.Flow

@Deprecated("Old code")
interface GlobalEventsRepository {

    suspend fun insert(globalEvent: GlobalEvent)

    fun getGlobalEvents(): Flow<List<GlobalEvent>>
}