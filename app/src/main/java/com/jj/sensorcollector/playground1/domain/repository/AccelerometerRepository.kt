package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.samples.AnalysedSample
import kotlinx.coroutines.flow.Flow

interface AccelerometerRepository {

    fun collectAccelerometerSamples(): Flow<SensorData>
    fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample>

    suspend fun sendSample()
}