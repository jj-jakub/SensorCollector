package com.jj.sensorcollector.playground1.data.monitors

import com.jj.core.coroutines.CoroutineScopeProvider
import com.jj.sensors.domain.managers.GPSManager
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.monitors.markers.GPSStateMonitor
import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensors.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow

class DefaultGPSStateMonitor(
    private val gpsRepository: GPSRepository,
    gpsManager: GPSManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: com.jj.core.coroutines.CoroutineScopeProvider
) : DefaultSampleCollectionStateMonitor<AnalysedSample.AnalysedGPSSample>(
    observeSamples = true,
    sensorManager = gpsManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
), GPSStateMonitor {

    override val maxIntervalBetweenSamplesMillis: Long = 10 * 1000L

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = gpsRepository.collectAnalysedGPSSamples()
}