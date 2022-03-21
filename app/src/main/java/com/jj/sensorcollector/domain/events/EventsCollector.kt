package com.jj.sensorcollector.domain.events

import com.jj.sensorcollector.data.sensors.GlobalEvent

interface EventsCollector {

    fun onEvent(event: GlobalEvent)
}