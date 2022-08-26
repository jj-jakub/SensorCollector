package com.jj.core.data.hardware.accelerometer.analysis

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.accelerometer.analysis.AccelerometerSampleAnalyser
import com.jj.domain.hardware.accelerometer.analysis.AccelerometerThresholdAnalyser
import com.jj.domain.hardware.general.SensorsRepository
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.time.TimeProvider

class DefaultAccelerometerSampleAnalyser(
    private val sensorsRepository: SensorsRepository,
    private val accelerometerThresholdAnalyser: AccelerometerThresholdAnalyser,
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider
) : AccelerometerSampleAnalyser(
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
) {
    override val samplesFlow = sensorsRepository.collectRawAccelerometerSamples()

    override suspend fun onSampleAvailable(sensorData: SensorData) {
        if (sensorData is SensorData.AccSample) analyseSample(sensorData)
        else handleOtherSample(sensorData)
    }

    private suspend fun analyseSample(sensorData: SensorData.AccSample) {
        val analysedSample = accelerometerThresholdAnalyser.performAnalysis(sensorData)
        sensorsRepository.insertAnalysedAccelerometerSample(analysedSample)
    }
}