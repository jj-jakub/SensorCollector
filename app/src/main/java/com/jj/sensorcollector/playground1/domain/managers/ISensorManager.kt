package com.jj.sensorcollector.playground1.domain.managers

import com.jj.sensorcollector.playground1.domain.samples.SensorData
import kotlinx.coroutines.flow.Flow

interface ISensorManager {

    fun collectRawSensorSamples(): Flow<SensorData>
    fun collectIsActiveState(): Flow<Boolean>
}