package com.jj.domain.sensors.general

import com.jj.domain.sensors.model.SensorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ISensorManager {
    fun collectRawSensorSamples(): Flow<SensorData>
    fun collectIsActiveState(): StateFlow<Boolean>
}