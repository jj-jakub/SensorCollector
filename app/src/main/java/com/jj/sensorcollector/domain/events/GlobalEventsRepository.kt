package com.jj.sensorcollector.domain.events

import com.jj.sensorcollector.data.sensors.GlobalEvent
import kotlinx.coroutines.flow.Flow

interface GlobalEventsRepository {

    suspend fun insert(globalEvent: GlobalEvent)

    fun getGlobalEvents(): Flow<List<GlobalEvent>>
}