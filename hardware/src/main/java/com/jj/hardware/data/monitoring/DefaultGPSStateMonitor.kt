package com.jj.hardware.data.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.gps.manager.GPSManager
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.monitoring.GPSStateMonitor
import com.jj.domain.monitoring.model.SystemModuleState
import com.jj.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow

class DefaultGPSStateMonitor(
    private val gpsRepository: GPSRepository,
    gpsManager: GPSManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : DefaultSampleCollectionStateMonitor<SensorData, AnalysedSample.AnalysedGPSSample>( // TODO Make first param less generic
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