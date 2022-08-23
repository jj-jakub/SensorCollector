package com.jj.domain.sensors.general

import com.jj.domain.utils.BufferedMutableSharedFlow
import com.jj.domain.sensors.model.SensorData
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

/**
 * isActiveState - true as long as there are observers to flow, false otherwise
 */
abstract class SmartSensorManager<T> : ISensorManager {

    protected val sensorSamples = BufferedMutableSharedFlow<SensorData>()
    private val isActiveState = MutableStateFlow(false)

    // Call from child class after it is initialized to avoid null values being passed from constructor to
    // onInactive and onActive methods
    protected suspend fun start() {
        sensorSamples.subscriptionCount.actdisact2(
            onActive = {
                val registeredSuccessfully = onActive()
                if (registeredSuccessfully) {
                    isActiveState.value = true
                }
            },
            onInActive = {
                isActiveState.value = false
                onInactive()
            }
        )
    }

    override fun collectRawSensorSamples(): Flow<SensorData> = sensorSamples.asSharedFlow()
    override fun collectIsActiveState(): StateFlow<Boolean> = isActiveState.asStateFlow()

    protected abstract suspend fun onActive(): Boolean

    protected open fun onInactive() {
        /* no-op */
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

    private suspend fun StateFlow<Int>.actdisact2(onActive: suspend () -> Unit, onInActive: () -> Unit) {
        coroutineScope {
            this@actdisact2
                .onEach { count ->
                    val hasSubscribers = count > 0
                    if (hasSubscribers) {
                        if (!isActiveState.value) onActive()
                    }
                    else onInActive()
                }.launchIn(this)
        }
    }
}