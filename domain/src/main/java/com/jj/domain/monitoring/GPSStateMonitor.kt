package com.jj.domain.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.general.ISensorManager
import com.jj.domain.hardware.gps.manager.GPSManager
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.time.TimeProvider

abstract class GPSStateMonitor(
    observeSamples: Boolean,
    sensorManager: GPSManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
) : SampleCollectionStateMonitor<SensorData, AnalysedSample.AnalysedGPSSample>(
    observeSamples = observeSamples,
    sensorManager = sensorManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider,
)