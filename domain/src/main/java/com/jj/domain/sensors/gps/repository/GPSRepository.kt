package com.jj.domain.sensors.gps.repository

import com.jj.domain.model.analysis.analysis.AnalysedSample
import com.jj.domain.sensors.model.SensorData
import kotlinx.coroutines.flow.Flow

interface GPSRepository {

    fun collectGPSSamples(): Flow<SensorData>
    fun collectAnalysedGPSSamples(): Flow<AnalysedSample.AnalysedGPSSample>
    suspend fun insertAnalysedGPSSample(analysedGPSSample: AnalysedSample.AnalysedGPSSample)
}