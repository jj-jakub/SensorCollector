package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.samples.AnalysedSample
import com.jj.sensorcollector.playground1.domain.samples.AnalysisResult
import kotlinx.coroutines.flow.Flow

interface SensorsRepository {

    fun collectAccelerometerSamples(): Flow<SensorData>
    fun collectAnalyzedAccelerometerSamples(): Flow<AnalysedSample>
    fun collectGyroscopeSamples(): Flow<SensorData>
    fun collectMagneticFieldSamples(): Flow<SensorData>

    suspend fun sendAccelerometerSample()
}