package com.jj.core.domain.repository

import com.jj.domain.sensors.model.SensorData
import com.jj.domain.model.analysis.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow

interface AccelerometerRepository {

    @Deprecated("This is not source of truth", replaceWith = ReplaceWith("collectAnalysedAccelerometerSamples"))
    fun collectRawAccelerometerSamples(): Flow<SensorData>
    fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample.AnalysedAccSample>

    suspend fun insertAnalysedAccelerometerSample(analysedAccSample: AnalysedSample.AnalysedAccSample)

    suspend fun sendSample()
}