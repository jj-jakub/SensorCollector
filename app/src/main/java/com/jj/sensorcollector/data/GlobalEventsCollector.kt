package com.jj.sensorcollector.data

import android.util.Log
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.data.sensors.GlobalEvent
import com.jj.sensorcollector.domain.events.EventsCollector
import com.jj.sensorcollector.domain.events.GlobalEventsRepository
import kotlinx.coroutines.launch

class GlobalEventsCollector(
    private val repository: GlobalEventsRepository,
    private val coroutineScopeProvider: CoroutineScopeProvider
) : EventsCollector {

    override fun onEvent(event: GlobalEvent) {
        Log.d("ABAB", "onEvent, type: ${event.eventType}, time: ${event.eventTime}")
        coroutineScopeProvider.getIOScope().launch {
            repository.insert(event)
        }
    }
}