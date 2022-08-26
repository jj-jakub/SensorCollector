package com.jj.domain.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.accelerometer.manager.AccelerometerManager
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.time.TimeProvider

abstract class AccelerometerStateMonitor(
    observeSamples: Boolean,
    sensorManager: AccelerometerManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
) :
    SampleCollectionStateMonitor<SensorData, AnalysedSample.AnalysedAccSample>(
        observeSamples = observeSamples,
        sensorManager = sensorManager,
        timeProvider = timeProvider,
        coroutineScopeProvider = coroutineScopeProvider,
    )