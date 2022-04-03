package com.jj.sensorcollector.playground1.domain.managers

import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import com.jj.sensorcollector.playground1.domain.SensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class SmartSensorManager(scope: CoroutineScope): ISensorManager {

    protected val sensorSamples = BufferedMutableSharedFlow<SensorData>()

    init {
        scope.launch {
            sensorSamples.subscriptionCount.actdisact(
                onActive = { onActive() },
                onInActive = { onInactive() }
            )
        }
    }

    override fun collectSensorSamples(): Flow<SensorData> = sensorSamples.asSharedFlow()

    abstract fun onActive()

    abstract fun onInactive()

    private suspend fun StateFlow<Int>.actdisact(onActive: () -> Unit, onInActive: () -> Unit) {
        coroutineScope {
            this@actdisact.map { count -> count > 0 }
                .distinctUntilChanged()
                .onEach { isActive ->
                    if (isActive) onActive()
                    else onInActive()
                }.launchIn(this)
        }
    }
}