package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensorcollector.playground1.domain.managers.GPSManager
import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import kotlinx.coroutines.flow.Flow

class DefaultGPSRepository(
    private val gpsManager: GPSManager
) : GPSRepository {

    override fun collectGPSSamples(): Flow<SensorData> = gpsManager.collectRawSensorSamples()
}