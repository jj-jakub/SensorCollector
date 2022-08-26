package com.jj.core.data.hardware.gps.analysis

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.gps.analysis.GPSSampleAnalyser
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.time.TimeProvider
import kotlinx.coroutines.flow.Flow

class DefaultGPSSampleAnalyser(
    private val timeProvider: TimeProvider,
    private val gpsRepository: GPSRepository,
    coroutineScopeProvider: CoroutineScopeProvider
) : GPSSampleAnalyser(
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
) {
    override val samplesFlow: Flow<SensorData> = gpsRepository.collectRawGPSSamples()

    override suspend fun onSampleAvailable(sensorData: SensorData) {
        if (sensorData is SensorData.GPSSample) performAnalysis(sensorData)
        else handleOtherSample(sensorData)
    }

    private suspend fun performAnalysis(sensorData: SensorData.GPSSample) {
        val analysedSample = AnalysedSample.AnalysedGPSSample(
            latitude = sensorData.latitude,
            longitude = sensorData.longitude,
            sampleTime = timeProvider.getNowMillis()
        )
        gpsRepository.insertAnalysedGPSSample(analysedSample)
    }
}