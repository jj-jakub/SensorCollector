package com.jj.domain.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.gyroscope.manager.GyroscopeManager
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.time.TimeProvider

abstract class GyroscopeStateMonitor(
    observeSamples: Boolean,
    sensorManager: GyroscopeManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
) : SampleCollectionStateMonitor<SensorData, AnalysedSample.AnalysedGyroscopeSample>(
    observeSamples = observeSamples,
    sensorManager = sensorManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider,
)