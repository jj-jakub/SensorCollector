package com.jj.hardware.data.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.magneticfield.manager.MagneticFieldManager
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.monitoring.MagneticFieldStateMonitor
import com.jj.domain.time.TimeProvider
import kotlinx.coroutines.flow.flow

class DefaultMagneticFieldStateMonitor(
    magneticFieldManager: MagneticFieldManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : MagneticFieldStateMonitor(
    observeSamples = false,
    sensorManager = magneticFieldManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
) {
    override fun analysedSamplesFlow() = flow<SensorData> {} // MagneticField doesn't have analyser
}