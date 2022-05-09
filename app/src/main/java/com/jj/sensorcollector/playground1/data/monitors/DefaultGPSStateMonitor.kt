package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.playground1.domain.managers.GPSManager
import com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager
import com.jj.sensorcollector.playground1.domain.managers.MagneticFieldManager
import com.jj.sensorcollector.playground1.domain.monitors.SampleCollectionStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.monitors.markers.GPSStateMonitor
import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow

class DefaultGPSStateMonitor(
    private val gpsRepository: GPSRepository,
    gpsManager: GPSManager,
    timeProvider: TimeProvider
) : DefaultSampleCollectionStateMonitor<AnalysedSample.AnalysedGPSSample>(
    sensorManager = gpsManager, timeProvider = timeProvider
), GPSStateMonitor {

    override val maxIntervalBetweenSamplesMillis: Long = 10 * 1000L

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = gpsRepository.collectAnalysedGPSSamples()
}