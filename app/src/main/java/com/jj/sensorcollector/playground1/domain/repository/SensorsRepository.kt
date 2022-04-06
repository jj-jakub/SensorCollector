package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow

interface SensorsRepository {

    fun collectRawAccelerometerSamples(): Flow<SensorData>
    fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample.AnalysedAccSample>
    suspend fun insertAnalysedAccelerometerSample(analysedAccSample: AnalysedSample.AnalysedAccSample)
    suspend fun sendAccelerometerSample()

    fun collectGyroscopeSamples(): Flow<SensorData>
    fun collectMagneticFieldSamples(): Flow<SensorData>

}