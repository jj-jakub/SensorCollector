package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensorcollector.playground1.data.database.gps.AnalysedGPSSampleDao
import com.jj.sensorcollector.playground1.data.database.gps.toAnalysedGPSSample
import com.jj.sensorcollector.playground1.data.database.gps.toAnalysedGPSSampleEntity
import com.jj.sensorcollector.playground1.domain.managers.GPSManager
import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class DefaultGPSRepository(
    private val gpsManager: GPSManager,
    private val analysedGPSSampleDao: AnalysedGPSSampleDao
) : GPSRepository {

    override fun collectGPSSamples(): Flow<SensorData> = gpsManager.collectRawSensorSamples()

    override fun collectAnalysedGPSSamples(): Flow<AnalysedSample.AnalysedGPSSample> =
        analysedGPSSampleDao.getLatestAnalysedGPSSampleEntity().filterNotNull()
            .map { entity -> entity.toAnalysedGPSSample() }


    override suspend fun insertAnalysedGPSSample(analysedGPSSample: AnalysedSample.AnalysedGPSSample) =
        analysedGPSSampleDao.insert(analysedGPSSample.toAnalysedGPSSampleEntity())

}