package com.jj.hardware.data.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.magneticfield.manager.MagneticFieldManager
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.monitoring.MagneticFieldStateMonitor
import com.jj.domain.monitoring.model.SystemModuleState
import com.jj.domain.time.TimeProvider
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