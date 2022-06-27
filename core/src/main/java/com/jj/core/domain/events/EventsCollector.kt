package com.jj.core.domain.events

import com.jj.core.data.sensors.GlobalEvent

interface EventsCollector {

    fun onEvent(event: GlobalEvent)
}