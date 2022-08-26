package com.jj.hardware.data.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.general.ISensorManager
import com.jj.domain.hardware.general.model.SensorActivityState
import com.jj.domain.monitoring.SampleCollectionStateMonitor
import com.jj.domain.monitoring.model.SystemModuleState
import com.jj.domain.time.TimeProvider
import com.jj.domain.utils.shouldStartNewJob
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

private const val FALLBACK_CHECK_INTERVAL = 500L

abstract class DefaultSampleCollectionStateMonitor<RawSampleType, AnalysedSampleType>(
    private val observeSamples: Boolean,
    private val sensorManager: ISensorManager<RawSampleType>,
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

    protected abstract fun analysedSamplesFlow(): Flow<AnalysedSampleType>

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
                sensorManager.collectSensorActivityState().collect { activityState ->
                    when (activityState) {
                        SensorActivityState.Active -> changeCollectionState(SystemModuleState.Working)
                        is SensorActivityState.Off -> {
                            timeSinceTurnedOff = timeProvider.getNowMillis()
                            if (activityState is SensorActivityState.Off.Error) {
                                changeCollectionState(SystemModuleState.Off.Error(activityState.message))
                            } else {
                                changeCollectionState(SystemModuleState.Off.Inactive)
                            }
                        }
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
                    if (isTimeBetweenSamplesExceeded(currentTime)) {
                        checkSensorActivityState()
                    } else {
                        // When collector is stopped manually, then Working state flickers because analyser
                        // saves sample some time after collector had been stopped
                        if (timeProvider.getNowMillis() - timeSinceTurnedOff > maxIntervalBetweenSamplesMillis) {
                            changeCollectionState(SystemModuleState.Working)
                        }
                    }
                    delay(FALLBACK_CHECK_INTERVAL)
                }
            }
        }
    }

    private fun isTimeBetweenSamplesExceeded(currentTime: Long): Boolean =
        currentTime - timeSinceLastSample > maxIntervalBetweenSamplesMillis

    private suspend fun checkSensorActivityState() {
        when (val state = sensorManager.collectSensorActivityState().value) {
            is SensorActivityState.Active -> {
                changeCollectionState(SystemModuleState.Off.OnButTimeExceeded)
            }
            is SensorActivityState.Off -> {
                if (state is SensorActivityState.Off.Error) {
                    changeCollectionState(SystemModuleState.Off.Error(state.message))
                } else {
                    changeCollectionState(SystemModuleState.Off.Inactive)
                }
            }
        }
    }

    private suspend fun changeCollectionState(state: SystemModuleState) {
        _sampleCollectionState.emit(state)
        // Other logic
    }
}