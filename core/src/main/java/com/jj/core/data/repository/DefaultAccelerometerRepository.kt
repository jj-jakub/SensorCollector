package com.jj.core.data.repository

import com.jj.core.data.database.accelerometer.AnalysedAccelerometerSampleDao
import com.jj.core.data.database.accelerometer.toAccelerationDataEntity
import com.jj.core.data.database.accelerometer.toAnalysedAccSample
import com.jj.core.domain.repository.AccelerometerRepository
import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.samples.analysis.AnalysedSample
import com.jj.core.domain.api.AccelerometerAPI
import com.jj.core.domain.sensors.interfaces.AccelerometerManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

@Deprecated("Old code")
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