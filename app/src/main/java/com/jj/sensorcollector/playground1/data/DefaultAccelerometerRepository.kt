package com.jj.sensorcollector.playground1.data

import com.jj.sensorcollector.playground1.domain.AccelerometerManager
import com.jj.sensorcollector.playground1.domain.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.api.AccelerometerService
import kotlinx.coroutines.flow.Flow

class DefaultAccelerometerRepository(
    private val accelerometerManager: AccelerometerManager,
    private val accelerometerService: AccelerometerService
) : AccelerometerRepository {

    override fun collectAccelerometerSamples(): Flow<SensorData> = accelerometerManager.collectAccelerometerSamples()

    override suspend fun sendSample() = accelerometerService.sendSample()
}