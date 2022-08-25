package com.jj.domain.hardware.gyroscope.repository

import com.jj.domain.hardware.model.SensorData
import kotlinx.coroutines.flow.Flow

interface GyroscopeRepository {

    fun collectGyroscopeSamples(): Flow<SensorData>
}