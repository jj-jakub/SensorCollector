package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensorcollector.playground1.data.database.accelerometer.AnalysedAccelerometerSampleDao
import com.jj.sensorcollector.playground1.data.database.accelerometer.toAccelerationDataEntity
import com.jj.sensorcollector.playground1.data.database.accelerometer.toAnalysedAccSample
import com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.api.AccelerometerService
import com.jj.sensorcollector.playground1.domain.samples.AnalysedSample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultAccelerometerRepository(
    private val accelerometerManager: AccelerometerManager,
    private val accelerometerService: AccelerometerService,
    private val analysedAccelerometerSampleDao: AnalysedAccelerometerSampleDao,
) : AccelerometerRepository {

    @Deprecated("This is not source of truth", replaceWith = ReplaceWith("collectAnalysedAccelerometerSamples"))
    override fun collectRawAccelerometerSamples(): Flow<SensorData> = accelerometerManager.collectRawSensorSamples()

    override fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample> =
        analysedAccelerometerSampleDao.getLatestAnalysedAccelerationSampleEntity()
            .map { entity -> entity.toAnalysedAccSample() }


    override suspend fun insertAnalysedAccelerometerSample(analysedAccSample: AnalysedSample.AnalysedAccSample) =
        analysedAccelerometerSampleDao.insert(analysedAccSample.toAccelerationDataEntity())

    override suspend fun sendSample() = accelerometerService.sendSample()
}