package com.jj.sensorcollector.data.repository

import com.jj.core.data.database.samples.accelerometer.AccelerationDataDao
import com.jj.core.data.database.samples.accelerometer.toAccelerationDataEntity
import com.jj.core.data.database.samples.accelerometer.toAccelerometerData
import com.jj.core.data.database.samples.gps.GPSDataDao
import com.jj.core.data.database.samples.gps.toGPSData
import com.jj.core.data.database.samples.gps.toGPSDataEntity
import com.jj.core.domain.sensors.SamplesRepository
import com.jj.core.domain.sensors.SensorData.AccelerometerData
import com.jj.core.domain.sensors.SensorData.GPSData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultSamplesRepository(
    private val gpsDataDao: GPSDataDao,
    private val accelerationDataDao: AccelerationDataDao
) : SamplesRepository {

    override suspend fun insert(accelerationSample: AccelerometerData) {
        accelerationDataDao.insert(accelerationSample.toAccelerationDataEntity())
    }

    override suspend fun insert(gpsData: GPSData) {
        gpsDataDao.insert(gpsData.toGPSDataEntity())
    }

    override fun getAccelerationSamples(): Flow<List<AccelerometerData>> {
        return accelerationDataDao.getAccelerationSamples().map { samples -> samples.map { sample -> sample.toAccelerometerData() } }
    }

    override fun getGPSSamples(): Flow<List<GPSData>> {
        return gpsDataDao.getGPSSamples().map { events -> events.map { event -> event.toGPSData() } }
    }
}