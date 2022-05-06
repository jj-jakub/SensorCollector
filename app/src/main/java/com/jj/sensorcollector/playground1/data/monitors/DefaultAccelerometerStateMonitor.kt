package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.framework.utils.shouldStartNewJob
import com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager
import com.jj.sensorcollector.playground1.domain.monitors.AccelerometerStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

private const val MAX_INTERVAL_BETWEEN_SAMPLES = 500L

class DefaultAccelerometerStateMonitor(
    private val sensorsRepository: SensorsRepository,
    private val accelerometerManager: AccelerometerManager,
    private val timeProvider: TimeProvider
) : AccelerometerStateMonitor {

    private var timeSinceLastSample = 0L
    private var timeSinceTurnedOff = 0L

    private var collectorJob: Job? = null
    private var monitoringJob: Job? = null
    private var fallbackMonitoringJob: Job? = null

    private val _accelerometerCollectionState = MutableStateFlow<SystemModuleState>(SystemModuleState.Unknown)
    override val accelerometerCollectionState = _accelerometerCollectionState.asStateFlow()

    override fun startMonitoring() {
        startCollectorJob()
        startMonitoringJob()
        startFallbackMonitoringJob()
    }

    private fun startCollectorJob() {
        if (collectorJob.shouldStartNewJob()) {
            collectorJob = CoroutineScope(Dispatchers.IO).launch {
                sensorsRepository.collectAnalysedAccelerometerSamples().collect {
                    timeSinceLastSample = timeProvider.getNowMillis()
                }
            }
        }
    }

    private fun startMonitoringJob() {
        if (monitoringJob.shouldStartNewJob()) {
            CoroutineScope(Dispatchers.IO).launch {
                monitoringJob
                accelerometerManager.collectIsActiveState().collect { isActive ->
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
                    if (currentTime - timeSinceLastSample > MAX_INTERVAL_BETWEEN_SAMPLES) {
                        changeCollectionState(SystemModuleState.Off)
                    } else {
                        // When collector is stopped manually, then Working state flickers because analyser
                        // Saves sample some time after collector had been stopped
                        if (timeProvider.getNowMillis() - timeSinceTurnedOff > MAX_INTERVAL_BETWEEN_SAMPLES) {
                            changeCollectionState(SystemModuleState.Working)
                        }
                    }
                    delay(MAX_INTERVAL_BETWEEN_SAMPLES)
                }
            }
        }
    }

    private suspend fun changeCollectionState(state: SystemModuleState) {
        _accelerometerCollectionState.emit(state)
        // Other logic
    }
}