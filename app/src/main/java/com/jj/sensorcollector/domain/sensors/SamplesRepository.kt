package com.jj.sensorcollector.domain.sensors

import com.jj.sensorcollector.domain.sensors.SensorData.AccelerometerData
import com.jj.sensorcollector.domain.sensors.SensorData.GPSData
import kotlinx.coroutines.flow.Flow

interface SamplesRepository {

    suspend fun insert(accelerationSample: AccelerometerData)
    suspend fun insert(gpsData: GPSData)

    fun getAccelerationSamples(): Flow<List<AccelerometerData>>
    fun getGPSSamples(): Flow<List<GPSData>>
}