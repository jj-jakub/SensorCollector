package com.jj.domain.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.general.ISensorManager
import com.jj.domain.hardware.general.model.SensorActivityState
import com.jj.domain.monitoring.model.SystemModuleState
import com.jj.domain.monitoring.model.toSystemModuleState
import com.jj.domain.time.TimeProvider
import com.jj.domain.utils.shouldStartNewJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.math.abs

private const val FALLBACK_CHECK_INTERVAL = 500L

abstract class SampleCollectionStateMonitor<RawSampleType, AnalysedSampleType>(
    private val observeSamples: Boolean,
    private val sensorManager: ISensorManager<RawSampleType>,
    private val timeProvider: TimeProvider,
    private val coroutineScopeProvider: CoroutineScopeProvider
) {
    protected open val maxIntervalBetweenSamplesMillis = 500L

    private var timeSinceLastSample = 0L
    private var timeSinceTurnedOff = 0L

    private var collectorJob: Job? = null
    private var sensorActivityStateMonitoringJob: Job? = null
    private var fallbackMonitoringJob: Job? = null

    private val _sampleCollectionState = MutableStateFlow<SystemModuleState>(SystemModuleState.Unknown)
    val sampleCollectionState: StateFlow<SystemModuleState> = _sampleCollectionState.asStateFlow()

    protected abstract fun analysedSamplesFlow(): Flow<AnalysedSampleType>

    fun startMonitoring() {
        observeSensorActivityState()
        if (observeSamples) {
            startCollectorJob()
            startFallbackMonitoringJob()
        }
    }

    private fun observeSensorActivityState() {
        if (sensorActivityStateMonitoringJob.shouldStartNewJob()) {
            sensorActivityStateMonitoringJob = coroutineScopeProvider.getIOScope().launch {
                sensorManager.collectSensorActivityState().collect { activityState ->
                    checkSensorActivityState(
                        activityState = activityState,
                        timeExceeded = false
                    )
                }
            }
        }
    }

    private fun startCollectorJob() {
        if (collectorJob.shouldStartNewJob()) {
            collectorJob = coroutineScopeProvider.getIOScope().launch {
                analysedSamplesFlow().collect {
                    timeSinceLastSample = timeProvider.getNowMillis()
                    refreshSystemModuleState()
                }
            }
        }
    }

    private fun startFallbackMonitoringJob() {
        if (fallbackMonitoringJob.shouldStartNewJob()) {
            fallbackMonitoringJob = coroutineScopeProvider.getIOScope().launch {
                while (true) {
                    refreshSystemModuleState()
                    delay(FALLBACK_CHECK_INTERVAL)
                }
            }
        }
    }

    private suspend fun refreshSystemModuleState() {
        val currentTime = timeProvider.getNowMillis()
        if (isTimeBetweenSamplesExceeded(currentTime)) {
            checkSensorActivityState( // TODO Investigate why it gets stuck at TimeExceeded when GPS is turned off for ~10s and then turned on againa
                activityState = sensorManager.collectSensorActivityState().value,
                timeExceeded = true
            )
        } else {
            // When collector is stopped manually, then Working state flickers because analyser
            // saves sample some time after collector had been stopped
            if (abs(timeProvider.getNowMillis() - timeSinceTurnedOff) > maxIntervalBetweenSamplesMillis) {
                changeCollectionState(SystemModuleState.Working)
            }
        }
    }

    private fun isTimeBetweenSamplesExceeded(currentTime: Long): Boolean =
        abs(currentTime - timeSinceLastSample) > maxIntervalBetweenSamplesMillis

    private suspend fun checkSensorActivityState(activityState: SensorActivityState, timeExceeded: Boolean) {
        activityState.toSystemModuleState(timeBetweenSamplesExceeded = timeExceeded).let { systemModuleState ->
            if (systemModuleState is SystemModuleState.Off) timeSinceTurnedOff = timeProvider.getNowMillis()
            changeCollectionState(systemModuleState)
        }
    }

    private suspend fun changeCollectionState(state: SystemModuleState) {
        _sampleCollectionState.emit(state)
        // Other logic
    }
}