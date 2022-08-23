package com.jj.sensors.data.monitors

import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.managers.MagneticFieldManager
import com.jj.core.domain.monitors.SystemModuleState
import com.jj.core.domain.time.TimeProvider
import com.jj.domain.sensors.model.SensorData
import com.jj.sensors.domain.monitors.markers.MagneticFieldStateMonitor
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class DefaultMagneticFieldStateMonitor(
    magneticFieldManager: MagneticFieldManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : DefaultSampleCollectionStateMonitor<SensorData, SensorData>(
    observeSamples = false,
    sensorManager = magneticFieldManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
), MagneticFieldStateMonitor {

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = flow<SensorData> {} // MagneticField doesn't have analyser
}