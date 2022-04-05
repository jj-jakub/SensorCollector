package com.jj.sensorcollector.playground1.domain.managers

import android.util.Log
import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import com.jj.sensorcollector.playground1.domain.SensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

abstract class SmartSensorManager(private val sensorType: Int) : ISensorManager {

    protected val sensorSamples = BufferedMutableSharedFlow<SensorData>()

    init {
        Log.d("ABABX", "${hashCode()} base init, stype: $sensorType")
    }

    // Call from child class after it is initialized to avoid null values being passed from constructor to
    // onInactive and onActive methods
    protected suspend fun start() {
            sensorSamples.subscriptionCount.actdisact(
                onActive = { onActive() },
                onInActive = {
                    Log.d("ABABX", "${this@SmartSensorManager.hashCode()} calling onInactive() stype: $sensorType")
                    onInactive()
                }
            )
    }

    override fun collectRawSensorSamples(): Flow<SensorData> = sensorSamples.asSharedFlow()

    protected open fun onActive() {
        Log.d("ABABX", "listener for sensor $sensorType has become active")
    }

    protected open fun onInactive() {
        Log.d("ABABX", "listener for sensor $sensorType has become inactive")
    }

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