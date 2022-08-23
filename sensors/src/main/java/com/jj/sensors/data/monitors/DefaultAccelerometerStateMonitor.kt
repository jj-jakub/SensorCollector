package com.jj.sensors.data.monitors

import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.monitors.SystemModuleState
import com.jj.domain.sensors.general.SensorsRepository
import com.jj.sensors.domain.monitors.markers.AccelerometerStateMonitor
import com.jj.core.domain.sensors.interfaces.AccelerometerManager
import com.jj.core.domain.time.TimeProvider
import com.jj.domain.model.analysis.analysis.AnalysedSample
import com.jj.domain.sensors.model.SensorData
import kotlinx.coroutines.flow.StateFlow

class DefaultAccelerometerStateMonitor(
    private val sensorsRepository: SensorsRepository,
    accelerometerManager: AccelerometerManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : DefaultSampleCollectionStateMonitor<SensorData, AnalysedSample.AnalysedAccSample>(
    observeSamples = true,
    sensorManager = accelerometerManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
), AccelerometerStateMonitor {

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = sensorsRepository.collectAnalysedAccelerometerSamples()
}