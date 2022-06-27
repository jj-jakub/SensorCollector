package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.playground1.domain.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager
import com.jj.sensorcollector.playground1.domain.monitors.SampleCollectionStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.monitors.markers.GyroscopeStateMonitor
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow

class DefaultGyroscopeStateMonitor(
    private val sensorsRepository: SensorsRepository,
    gyroscopeManager: GyroscopeManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : DefaultSampleCollectionStateMonitor<SensorData>(
    observeSamples = false,
    sensorManager = gyroscopeManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
), GyroscopeStateMonitor {

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = sensorsRepository.collectGyroscopeSamples()
}