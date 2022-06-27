package com.jj.sensorcollector.data.repository

import com.jj.core.data.database.events.GlobalEventDataDao
import com.jj.core.data.database.events.toGlobalEvent
import com.jj.core.data.database.events.toGlobalEventEntity
import com.jj.core.data.sensors.GlobalEvent
import com.jj.core.domain.events.GlobalEventsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultGlobalEventRepository(
        private val globalEventDataDao: GlobalEventDataDao
) : GlobalEventsRepository {

    override suspend fun insert(globalEvent: GlobalEvent) {
        globalEventDataDao.insert(globalEvent.toGlobalEventEntity())
    }

    override fun getGlobalEvents(): Flow<List<GlobalEvent>> {
        return globalEventDataDao.getGlobalEvents().map { events -> events.map { event -> event.toGlobalEvent() } }
    }
}