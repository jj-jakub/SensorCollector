package com.jj.core.data.hardware.gyroscope

import com.jj.domain.hardware.model.SensorData
import com.jj.domain.hardware.gyroscope.manager.GyroscopeManager
import com.jj.domain.hardware.gyroscope.repository.GyroscopeRepository
import kotlinx.coroutines.flow.Flow

class DefaultGyroscopeRepository(
    private val gyroscopeManager: GyroscopeManager
) : GyroscopeRepository {

    override fun collectGyroscopeSamples(): Flow<SensorData> = gyroscopeManager.collectRawSensorSamples()
}