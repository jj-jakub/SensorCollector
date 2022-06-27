package com.jj.sensors.domain.managers

import com.jj.sensors.domain.samples.SensorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ISensorManager {

    fun collectRawSensorSamples(): Flow<SensorData>
    fun collectIsActiveState(): StateFlow<Boolean>
}