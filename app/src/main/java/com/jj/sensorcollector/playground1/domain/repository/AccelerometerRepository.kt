package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensors.domain.samples.SensorData
import com.jj.sensors.domain.samples.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow

interface AccelerometerRepository {

    @Deprecated("This is not source of truth", replaceWith = ReplaceWith("collectAnalysedAccelerometerSamples"))
    fun collectRawAccelerometerSamples(): Flow<SensorData>
    fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample.AnalysedAccSample>

    suspend fun insertAnalysedAccelerometerSample(analysedAccSample: AnalysedSample.AnalysedAccSample)

    suspend fun sendSample()
}