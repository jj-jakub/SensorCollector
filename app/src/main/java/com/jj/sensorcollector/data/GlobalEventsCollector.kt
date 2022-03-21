package com.jj.sensorcollector.data

import android.util.Log
import com.jj.sensorcollector.data.sensors.GlobalEvent
import com.jj.sensorcollector.domain.events.EventsCollector
import com.jj.sensorcollector.domain.events.GlobalEventsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class GlobalEventsCollector(
        private val repository: GlobalEventsRepository
) : EventsCollector {

    override fun onEvent(event: GlobalEvent) {
        Log.d("ABAB", "onEvent, type: ${event.eventType}, time: ${event.eventTime}")
        CoroutineScope(Dispatchers.IO).launch {
            repository.insert(event)
        }
    }
}