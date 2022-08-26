package com.jj.domain.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.magneticfield.manager.MagneticFieldManager
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.time.TimeProvider

abstract class MagneticFieldStateMonitor(
    observeSamples: Boolean,
    sensorManager: MagneticFieldManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
) : SampleCollectionStateMonitor<SensorData, SensorData>(
    observeSamples = observeSamples,
    sensorManager = sensorManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider,
)