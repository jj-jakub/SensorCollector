package com.jj.sensorcollector.playground1.data

import com.jj.sensorcollector.playground1.domain.AccelerometerManager
import com.jj.sensorcollector.playground1.domain.AccelerometerRepository
import com.jj.sensorcollector.playground1.framework.SensorData
import kotlinx.coroutines.flow.Flow

class DefaultAccelerometerRepository(
    private val accelerometerManager: AccelerometerManager
) : AccelerometerRepository {

    override fun collectAccelerometerSamples(): Flow<SensorData> = accelerometerManager.collectAccelerometerSamples()
}