package com.jj.core.data.repository

import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.managers.GyroscopeManager
import com.jj.core.domain.repository.GyroscopeRepository
import kotlinx.coroutines.flow.Flow

class DefaultGyroscopeRepository(
    private val gyroscopeManager: GyroscopeManager
) : GyroscopeRepository {

    override fun collectGyroscopeSamples(): Flow<SensorData> = gyroscopeManager.collectRawSensorSamples()
}