package com.jj.sensors.data.monitors

import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.monitors.SystemModuleState
import com.jj.core.domain.sensors.interfaces.GPSManager
import com.jj.core.domain.time.TimeProvider
import com.jj.domain.gps.GPSRepository
import com.jj.domain.model.analysis.analysis.AnalysedSample
import com.jj.sensors.domain.monitors.markers.GPSStateMonitor
import kotlinx.coroutines.flow.StateFlow

class DefaultGPSStateMonitor(
    private val gpsRepository: GPSRepository,
    gpsManager: GPSManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
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