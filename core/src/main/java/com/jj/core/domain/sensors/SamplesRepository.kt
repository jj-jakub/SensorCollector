package com.jj.core.domain.sensors

import com.jj.core.domain.sensors.SensorData.AccelerometerData
import com.jj.core.domain.sensors.SensorData.GPSData
import kotlinx.coroutines.flow.Flow

interface SamplesRepository {

    suspend fun insert(accelerationSample: AccelerometerData)
    suspend fun insert(gpsData: GPSData)

    fun getAccelerationSamples(): Flow<List<AccelerometerData>>
    fun getGPSSamples(): Flow<List<GPSData>>
}