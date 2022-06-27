package com.jj.core.data.database.events

import com.jj.core.data.sensors.GlobalEvent

fun GlobalEventEntity.toGlobalEvent() = GlobalEvent(
        eventTime = time,
        eventType = type
)

fun GlobalEvent.toGlobalEventEntity() = GlobalEventEntity(
        time = eventTime,
        type = eventType
)