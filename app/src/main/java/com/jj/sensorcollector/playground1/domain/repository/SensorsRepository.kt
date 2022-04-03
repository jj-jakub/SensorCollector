package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensorcollector.playground1.domain.SensorData
import kotlinx.coroutines.flow.Flow

interface SensorsRepository {

    fun collectAccelerometerSamples(): Flow<SensorData>
    fun collectGyroscopeSamples(): Flow<SensorData>
    fun collectMagneticFieldSamples(): Flow<SensorData>

    suspend fun sendAccelerometerSample()
}