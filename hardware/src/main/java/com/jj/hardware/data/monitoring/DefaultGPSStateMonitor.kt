package com.jj.hardware.data.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.gps.manager.GPSManager
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.monitoring.GPSStateMonitor
import com.jj.domain.time.TimeProvider

class DefaultGPSStateMonitor(
    private val gpsRepository: GPSRepository,
    gpsManager: GPSManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
) : GPSStateMonitor( // TODO Make first param less generic
    observeSamples = true,
    sensorManager = gpsManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider,
)  {

    override val maxIntervalBetweenSamplesMillis: Long = 10 * 1000L

    override fun analysedSamplesFlow() = gpsRepository.collectAnalysedGPSSamples()
}