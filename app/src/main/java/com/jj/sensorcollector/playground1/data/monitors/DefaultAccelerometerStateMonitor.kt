package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.framework.utils.shouldStartNewJob
import com.jj.sensorcollector.playground1.domain.monitors.AccelerometerStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.monitors.SystemStateMonitor
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
    private val timeProvider: TimeProvider
) : AccelerometerStateMonitor {

    private var timeSinceLastSample = 0L

    private var collectorJob: Job? = null
    private var monitoringJob: Job? = null

    private val _accelerometerCollectionState = MutableStateFlow<SystemModuleState>(SystemModuleState.Unknown)
    override val accelerometerCollectionState = _accelerometerCollectionState.asStateFlow()

    override fun startMonitoring() {
        if (collectorJob.shouldStartNewJob()) {
            collectorJob = CoroutineScope(Dispatchers.IO).launch {
                sensorsRepository.collectAnalysedAccelerometerSamples().collect {
                    timeSinceLastSample = timeProvider.getNowMillis()
                }
            }
        }

        if (monitoringJob.shouldStartNewJob()) {
            monitoringJob = CoroutineScope(Dispatchers.IO).launch {
                while (true) {
                    val currentTime = timeProvider.getNowMillis()
                    if (currentTime - timeSinceLastSample > MAX_INTERVAL_BETWEEN_SAMPLES) {
                        _accelerometerCollectionState.emit(SystemModuleState.Off)
                    } else {
                        _accelerometerCollectionState.emit(SystemModuleState.Working)
                    }
                    delay(MAX_INTERVAL_BETWEEN_SAMPLES)
                }
            }
        }
    }
}