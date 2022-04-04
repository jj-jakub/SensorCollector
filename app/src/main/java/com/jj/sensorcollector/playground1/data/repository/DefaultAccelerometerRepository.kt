package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.api.AccelerometerService
import com.jj.sensorcollector.playground1.domain.samples.AccThresholdAnalyzer
import com.jj.sensorcollector.playground1.domain.samples.AnalysedSample
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class DefaultAccelerometerRepository(
    private val accelerometerManager: AccelerometerManager,
    private val accelerometerService: AccelerometerService,
    private val accThresholdAnalyzer: AccThresholdAnalyzer
) : AccelerometerRepository {

    override fun collectAccelerometerSamples(): Flow<SensorData> = accelerometerManager.collectSensorSamples()

    override fun collectAnalysedAccelerometerSamples(): Flow<AnalysedSample> =
        accelerometerManager.collectSensorSamples().map {
            when (it) {
                is SensorData.AccSample -> accThresholdAnalyzer.analyze(it)
                is SensorData.Error -> AnalysedSample.Error(it, it.msg)
                else -> AnalysedSample.Error(it, "WrongSample")
            }
        }

    override suspend fun sendSample() = accelerometerService.sendSample()
}