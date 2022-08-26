package com.jj.domain.hardware.accelerometer.repository

import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow

interface AccelerometerRepository {
    @Deprecated("This is not source of truth", replaceWith = ReplaceWith("collectAnalysedAccelerometerSamples"))
    fun collectRawAccelerometerSamples(): Flow<SensorData>
    fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample.AnalysedAccSample>

    suspend fun insertAnalysedAccelerometerSample(analysedAccSample: AnalysedSample.AnalysedAccSample)

    suspend fun sendSample()
}