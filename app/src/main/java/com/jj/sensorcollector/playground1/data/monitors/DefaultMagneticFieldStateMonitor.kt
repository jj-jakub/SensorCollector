package com.jj.sensorcollector.playground1.data.monitors

import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.sensors.domain.managers.MagneticFieldManager
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.monitors.markers.MagneticFieldStateMonitor
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensors.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
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