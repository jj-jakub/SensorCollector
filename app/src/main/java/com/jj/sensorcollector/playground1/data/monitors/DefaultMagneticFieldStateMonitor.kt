package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager
import com.jj.sensorcollector.playground1.domain.managers.MagneticFieldManager
import com.jj.sensorcollector.playground1.domain.monitors.SampleCollectionStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.monitors.markers.MagneticFieldStateMonitor
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow

class DefaultMagneticFieldStateMonitor(
    private val sensorsRepository: SensorsRepository,
    magneticFieldManager: MagneticFieldManager,
    timeProvider: TimeProvider
) : DefaultSampleCollectionStateMonitor<SensorData>(
    observeSamples = false,
    sensorManager = magneticFieldManager, timeProvider = timeProvider
), MagneticFieldStateMonitor {

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = sensorsRepository.collectMagneticFieldSamples()
}