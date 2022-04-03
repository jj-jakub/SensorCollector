package com.jj.sensorcollector.playground1.domain

import kotlinx.coroutines.flow.Flow

interface AccelerometerManager {

    fun collectAccelerometerSamples(): Flow<SensorData>
}