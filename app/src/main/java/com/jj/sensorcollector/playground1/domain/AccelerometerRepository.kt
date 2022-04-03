package com.jj.sensorcollector.playground1.domain

import kotlinx.coroutines.flow.Flow

interface AccelerometerRepository {

    fun collectAccelerometerSamples(): Flow<SensorData>

    suspend fun sendSample()
}