package com.jj.core.data.hardware.accelerometer.repository

import com.jj.core.data.database.accelerometer.AnalysedAccelerometerSampleDao
import com.jj.core.data.database.accelerometer.toAccelerationDataEntity
import com.jj.core.data.database.accelerometer.toAnalysedAccSample
import com.jj.domain.hardware.accelerometer.repository.AccelerometerRepository
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.api.AccelerometerAPI
import com.jj.domain.hardware.accelerometer.manager.AccelerometerManager
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