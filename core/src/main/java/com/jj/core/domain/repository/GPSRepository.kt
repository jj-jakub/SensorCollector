package com.jj.core.domain.repository

import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.samples.analysis.AnalysedSample
import kotlinx.coroutines.flow.Flow

interface GPSRepository {

    fun collectGPSSamples(): Flow<SensorData>

    fun collectAnalysedGPSSamples(): Flow<AnalysedSample.AnalysedGPSSample>

    suspend fun insertAnalysedGPSSample(analysedGPSSample: AnalysedSample.AnalysedGPSSample)
}