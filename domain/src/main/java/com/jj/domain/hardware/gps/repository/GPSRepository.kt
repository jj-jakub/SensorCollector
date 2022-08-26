package com.jj.domain.hardware.gps.repository

import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.hardware.model.SensorData
import kotlinx.coroutines.flow.Flow

interface GPSRepository {
    @Deprecated("This is not source of truth", replaceWith = ReplaceWith("collectAnalysedGPSSamples"))
    fun collectRawGPSSamples(): Flow<SensorData>
    fun collectAnalysedGPSSamples(): Flow<AnalysedSample.AnalysedGPSSample>
    suspend fun insertAnalysedGPSSample(analysedGPSSample: AnalysedSample.AnalysedGPSSample)
}