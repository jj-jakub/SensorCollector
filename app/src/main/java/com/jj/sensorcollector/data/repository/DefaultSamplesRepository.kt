package com.jj.sensorcollector.data.repository

import com.jj.sensorcollector.data.database.samples.GPSDataDao
import com.jj.sensorcollector.data.database.samples.toGPSData
import com.jj.sensorcollector.data.database.samples.toGPSDataEntity
import com.jj.sensorcollector.domain.sensors.SamplesRepository
import com.jj.sensorcollector.domain.sensors.SensorData.AccelerometerData
import com.jj.sensorcollector.domain.sensors.SensorData.GPSData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultSamplesRepository(
        private val gpsDataDao: GPSDataDao
) : SamplesRepository {

    override suspend fun insert(accelerationSample: AccelerometerData) {
        TODO("Not yet implemented")
    }

    override suspend fun insert(gpsData: GPSData) {
        gpsDataDao.insert(gpsData.toGPSDataEntity())
    }

    override fun getAccelerationSamples(): Flow<List<AccelerometerData>> {
        TODO("Not yet implemented")
    }

    override fun getGPSSamples(): Flow<List<GPSData>> {
        return gpsDataDao.getGPSSamples().map { events -> events.map { event -> event.toGPSData() } }
    }
}