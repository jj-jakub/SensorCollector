package com.jj.hardware.data.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.accelerometer.manager.AccelerometerManager
import com.jj.domain.hardware.general.SensorsRepository
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.monitoring.AccelerometerStateMonitor
import com.jj.domain.monitoring.model.SystemModuleState
import com.jj.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow

class DefaultAccelerometerStateMonitor(
    private val sensorsRepository: SensorsRepository,
    accelerometerManager: AccelerometerManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : AccelerometerStateMonitor(
    observeSamples = true,
    sensorManager = accelerometerManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider,
) {
    override fun analysedSamplesFlow() = sensorsRepository.collectAnalysedAccelerometerSamples()
}