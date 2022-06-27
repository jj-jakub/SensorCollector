package com.jj.sensors.data.monitors

import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.repository.SensorsRepository
import com.jj.core.domain.managers.GyroscopeManager
import com.jj.core.domain.monitors.SystemModuleState
import com.jj.sensors.domain.monitors.markers.GyroscopeStateMonitor
import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.time.TimeProvider
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