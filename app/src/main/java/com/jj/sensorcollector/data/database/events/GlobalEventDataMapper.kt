package com.jj.sensorcollector.data.database.events

import com.jj.sensorcollector.data.sensors.GlobalEvent

fun GlobalEventEntity.toGlobalEvent() = GlobalEvent(
        eventTime = time,
        eventType = type
)

fun GlobalEvent.toGlobalEventEntity() = GlobalEventEntity(
        time = eventTime,
        type = eventType
)