package com.jj.domain.hardware.gps.repository

import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.hardware.model.SensorData
import kotlinx.coroutines.flow.Flow

interface GPSRepository {

    fun collectGPSSamples(): Flow<SensorData>
    fun collectAnalysedGPSSamples(): Flow<AnalysedSample.AnalysedGPSSample>
    suspend fun insertAnalysedGPSSample(analysedGPSSample: AnalysedSample.AnalysedGPSSample)
}