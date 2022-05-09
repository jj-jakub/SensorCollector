package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.framework.utils.shouldStartNewJob
import com.jj.sensorcollector.playground1.domain.managers.ISensorManager
import com.jj.sensorcollector.playground1.domain.monitors.SampleCollectionStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

abstract class DefaultSampleCollectionStateMonitor<SampleType>(
    private val sensorManager: ISensorManager,
    private val timeProvider: TimeProvider
) : SampleCollectionStateMonitor {

    protected open val maxIntervalBetweenSamplesMillis = 500L

    private var timeSinceLastSample = 0L
    private var timeSinceTurnedOff = 0L

    private var collectorJob: Job? = null
    private var monitoringJob: Job? = null
    private var fallbackMonitoringJob: Job? = null

    private val _sampleCollectionState = MutableStateFlow<SystemModuleState>(SystemModuleState.Unknown)
    override val sampleCollectionState: StateFlow<SystemModuleState> = _sampleCollectionState.asStateFlow()

    protected abstract fun analysedSamplesFlow(): Flow<SampleType>

    override fun startMonitoring() {
        startCollectorJob()
        startMonitoringJob()
        startFallbackMonitoringJob()
    }

    private fun startCollectorJob() {
        if (collectorJob.shouldStartNewJob()) {
            collectorJob = CoroutineScope(Dispatchers.IO).launch {
                analysedSamplesFlow().collect {
                    timeSinceLastSample = timeProvider.getNowMillis()
                }
            }
        }
    }

    private fun startMonitoringJob() {
        if (monitoringJob.shouldStartNewJob()) {
            CoroutineScope(Dispatchers.IO).launch {
                monitoringJob
                sensorManager.collectIsActiveState().collect { isActive ->
                    if (isActive) {
                        changeCollectionState(SystemModuleState.Working)
                    } else {
                        timeSinceTurnedOff = timeProvider.getNowMillis()
                        changeCollectionState(SystemModuleState.Off)
                    }
                }
            }
        }
    }

    private fun startFallbackMonitoringJob() {
        if (fallbackMonitoringJob.shouldStartNewJob()) {
            fallbackMonitoringJob = CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    val currentTime = timeProvider.getNowMillis()
                    if (currentTime - timeSinceLastSample > maxIntervalBetweenSamplesMillis) {
                        changeCollectionState(SystemModuleState.Off)
                    } else {
                        // When collector is stopped manually, then Working state flickers because analyser
                        // Saves sample some time after collector had been stopped
                        if (timeProvider.getNowMillis() - timeSinceTurnedOff > maxIntervalBetweenSamplesMillis) {
                            changeCollectionState(SystemModuleState.Working)
                        }
                    }
                    delay(maxIntervalBetweenSamplesMillis)
                }
            }
        }
    }

    private suspend fun changeCollectionState(state: SystemModuleState) {
        _sampleCollectionState.emit(state)
        // Other logic
    }
}