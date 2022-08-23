package com.jj.sensors.data.monitors

import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.sensors.general.SensorsRepository
import com.jj.core.domain.managers.GyroscopeManager
import com.jj.core.domain.monitors.SystemModuleState
import com.jj.sensors.domain.monitors.markers.GyroscopeStateMonitor
import com.jj.core.domain.time.TimeProvider
import com.jj.domain.sensors.model.SensorData
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class DefaultGyroscopeStateMonitor(
    gyroscopeManager: GyroscopeManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : DefaultSampleCollectionStateMonitor<SensorData, SensorData>(
    observeSamples = false,
    sensorManager = gyroscopeManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
), GyroscopeStateMonitor {

    override val sampleCollectionState: StateFlow<SystemModuleState>
        get() = super.sampleCollectionState

    override fun analysedSamplesFlow() = flow<SensorData> {} // Gyroscope doesn't have analyser
}