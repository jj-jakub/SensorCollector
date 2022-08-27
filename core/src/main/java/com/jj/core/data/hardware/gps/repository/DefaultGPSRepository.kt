package com.jj.core.data.hardware.gps.repository

import com.jj.core.data.database.gps.analysis.AnalysedGPSSampleDao
import com.jj.core.data.database.gps.analysis.toAnalysedGPSSample
import com.jj.core.data.database.gps.analysis.toAnalysedGPSSampleEntity
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.hardware.gps.manager.GPSManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map

class DefaultGPSRepository(
    private val gpsManager: GPSManager,
    private val analysedGPSSampleDao: AnalysedGPSSampleDao
) : GPSRepository {

    override fun collectRawGPSSamples(): Flow<SensorData> = gpsManager.collectRawSensorSamples()

    override fun collectAnalysedGPSSamples(): Flow<AnalysedSample.AnalysedGPSSample> =
        analysedGPSSampleDao.getLatestAnalysedGPSSampleEntity().filterNotNull()
            .map { entity -> entity.toAnalysedGPSSample() }


    override suspend fun insertAnalysedGPSSample(analysedGPSSample: AnalysedSample.AnalysedGPSSample) =
        analysedGPSSampleDao.insert(analysedGPSSample.toAnalysedGPSSampleEntity())

}