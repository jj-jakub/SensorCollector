package com.jj.domain.hardware.general

import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow

interface SensorsRepository {

    fun collectRawAccelerometerSamples(): Flow<SensorData>
    fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample.AnalysedAccSample>
    suspend fun insertAnalysedAccelerometerSample(analysedAccSample: AnalysedSample.AnalysedAccSample)
    suspend fun sendAccelerometerSample()

    fun collectGyroscopeSamples(): Flow<SensorData>
    fun collectMagneticFieldSamples(): Flow<SensorData>
}