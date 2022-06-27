package com.jj.sensors.data.monitors

import com.jj.core.framework.utils.shouldStartNewJob
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.monitors.SystemModuleState
import com.jj.core.domain.sensors.interfaces.ISensorManager
import com.jj.core.domain.time.TimeProvider
import com.jj.sensors.domain.monitors.SampleCollectionStateMonitor
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val FALLBACK_CHECK_INTERVAL = 500L

abstract class DefaultSampleCollectionStateMonitor<SampleType>(
    private val observeSamples: Boolean,
    private val sensorManager: ISensorManager,
    private val timeProvider: TimeProvider,
    private val coroutineScopeProvider: CoroutineScopeProvider
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
        startMonitoringJob()
        if (observeSamples) {
            startCollectorJob()
            startFallbackMonitoringJob()
        }
    }

    private fun startCollectorJob() {
        if (collectorJob.shouldStartNewJob()) {
            collectorJob = coroutineScopeProvider.getIOScope().launch {
                analysedSamplesFlow().collect {
                    timeSinceLastSample = timeProvider.getNowMillis()
                }
            }
        }
    }

    private fun startMonitoringJob() {
        if (monitoringJob.shouldStartNewJob()) {
            monitoringJob = coroutineScopeProvider.getIOScope().launch {
                sensorManager.collectIsActiveState().collect { isActive ->
                    if (isActive) {
                        changeCollectionState(SystemModuleState.Working)
                    } else {
                        timeSinceTurnedOff = timeProvider.getNowMillis()
                        changeCollectionState(SystemModuleState.Off())
                    }
                }
            }
        }
    }

    private fun startFallbackMonitoringJob() {
        if (fallbackMonitoringJob.shouldStartNewJob()) {
            fallbackMonitoringJob = coroutineScopeProvider.getIOScope().launch {
                while (true) {
                    val currentTime = timeProvider.getNowMillis()
                    if (currentTime - timeSinceLastSample > maxIntervalBetweenSamplesMillis) {
                        if (sensorManager.collectIsActiveState().value) {
                            changeCollectionState(SystemModuleState.Off.OnButTimeExceeded)
                        } else {
                            changeCollectionState(SystemModuleState.Off())
                        }
                    } else {
                        // When collector is stopped manually, then Working state flickers because analyser
                        // Saves sample some time after collector had been stopped
                        if (timeProvider.getNowMillis() - timeSinceTurnedOff > maxIntervalBetweenSamplesMillis) {
                            changeCollectionState(SystemModuleState.Working)
                        }
                    }
                    delay(FALLBACK_CHECK_INTERVAL)
                }
            }
        }
    }

    private suspend fun changeCollectionState(state: SystemModuleState) {
        _sampleCollectionState.emit(state)
        // Other logic
    }
}