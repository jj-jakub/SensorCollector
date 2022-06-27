package com.jj.sensors.data.monitors

import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.repository.SensorsRepository
import com.jj.core.domain.managers.MagneticFieldManager
import com.jj.core.domain.monitors.SystemModuleState
import com.jj.sensors.domain.monitors.markers.MagneticFieldStateMonitor
import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow

class DefaultMagneticFieldStateMonitor(
    private val sensorsRepository: SensorsRepository,
    magneticFieldManager: MagneticFieldManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : DefaultSampleCollectionStateMonitor<SensorData>(
    observeSamples = false,
    sensorManager = magneticFieldManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
), MagneticFieldStateMonitor {

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = sensorsRepository.collectMagneticFieldSamples()
}