package com.jj.sensorcollector.data

import android.util.Log
import com.jj.sensorcollector.data.sensors.GlobalEvent
import com.jj.sensorcollector.domain.events.EventsCollector

class GlobalEventsCollector : EventsCollector {

    override fun onEvent(event: GlobalEvent) {
        Log.d("ABAB", "onEvent, type: ${event.eventType}, time: ${event.eventTime}")
    }
}