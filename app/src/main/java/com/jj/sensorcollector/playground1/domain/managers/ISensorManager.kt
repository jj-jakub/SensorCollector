package com.jj.sensorcollector.playground1.domain.managers

import com.jj.sensorcollector.playground1.domain.SensorData
import kotlinx.coroutines.flow.Flow

interface ISensorManager {

    fun collectSensorSamples(): Flow<SensorData>
}