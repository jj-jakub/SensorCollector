package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager
import com.jj.sensorcollector.playground1.domain.repository.GyroscopeRepository
import kotlinx.coroutines.flow.Flow

class DefaultGyroscopeRepository(
    private val gyroscopeManager: GyroscopeManager
) : GyroscopeRepository {

    override fun collectGyroscopeSamples(): Flow<SensorData> = gyroscopeManager.collectSensorSamples()
}