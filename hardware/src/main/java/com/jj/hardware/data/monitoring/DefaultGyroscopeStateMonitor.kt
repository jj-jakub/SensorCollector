package com.jj.hardware.data.monitoring

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.general.ISensorManager
import com.jj.domain.hardware.gyroscope.manager.GyroscopeManager
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.monitoring.GyroscopeStateMonitor
import com.jj.domain.monitoring.SampleCollectionStateMonitor
import com.jj.domain.monitoring.model.SystemModuleState
import com.jj.domain.time.TimeProvider
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class DefaultGyroscopeStateMonitor(
    gyroscopeManager: GyroscopeManager,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
) : GyroscopeStateMonitor(
    observeSamples = false,
    sensorManager = gyroscopeManager,
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider,
) {
    override fun analysedSamplesFlow() = flow<AnalysedSample.AnalysedGyroscopeSample> {} // Gyroscope doesn't have analyser
}