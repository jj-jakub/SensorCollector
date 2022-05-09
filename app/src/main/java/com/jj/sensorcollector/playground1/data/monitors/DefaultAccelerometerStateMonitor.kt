package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager
import com.jj.sensorcollector.playground1.domain.monitors.markers.AccelerometerStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow

class DefaultAccelerometerStateMonitor(
    private val sensorsRepository: SensorsRepository,
    accelerometerManager: AccelerometerManager,
    timeProvider: TimeProvider
) : DefaultSampleCollectionStateMonitor<AnalysedSample.AnalysedAccSample>(
    observeSamples = true,
    sensorManager = accelerometerManager, timeProvider = timeProvider
), AccelerometerStateMonitor {

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = sensorsRepository.collectAnalysedAccelerometerSamples()
}