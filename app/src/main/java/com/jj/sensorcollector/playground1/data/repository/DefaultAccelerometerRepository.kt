package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensorcollector.playground1.data.database.accelerometer.AnalysedAccelerometerSampleDao
import com.jj.sensorcollector.playground1.data.database.accelerometer.toAccelerationDataEntity
import com.jj.sensorcollector.playground1.data.database.accelerometer.toAnalysedAccSample
import com.jj.sensors.domain.managers.AccelerometerManager
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensors.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.api.AccelerometerAPI
import com.jj.sensors.domain.samples.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class DefaultAccelerometerRepository(
    private val accelerometerManager: AccelerometerManager,
    private val accelerometerAPI: AccelerometerAPI,
    private val analysedAccelerometerSampleDao: AnalysedAccelerometerSampleDao,
) : AccelerometerRepository {

    override fun collectRawAccelerometerSamples(): Flow<SensorData> = accelerometerManager.collectRawSensorSamples()

    override fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample.AnalysedAccSample> =
        analysedAccelerometerSampleDao.getLatestAnalysedAccelerationSampleEntity().filterNotNull()
            .map { entity -> entity.toAnalysedAccSample() }


    override suspend fun insertAnalysedAccelerometerSample(analysedAccSample: AnalysedSample.AnalysedAccSample) =
        analysedAccelerometerSampleDao.insert(analysedAccSample.toAccelerationDataEntity())

    override suspend fun sendSample() = accelerometerAPI.sendSample()
}