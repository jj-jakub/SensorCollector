package com.jj.core.domain.repository

import com.jj.domain.sensors.model.SensorData
import kotlinx.coroutines.flow.Flow

interface GyroscopeRepository {

    fun collectGyroscopeSamples(): Flow<SensorData>
}