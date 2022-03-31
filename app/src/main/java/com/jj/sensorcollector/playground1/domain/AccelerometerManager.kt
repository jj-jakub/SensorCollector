package com.jj.sensorcollector.playground1.domain

import com.jj.sensorcollector.playground1.framework.SensorData
import kotlinx.coroutines.flow.Flow

interface AccelerometerManager {

    fun collectAccelerometerSamples(): Flow<SensorData>
}