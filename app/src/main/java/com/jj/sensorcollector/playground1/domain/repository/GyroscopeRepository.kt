package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensorcollector.playground1.domain.SensorData
import kotlinx.coroutines.flow.Flow

interface GyroscopeRepository {

    fun collectGyroscopeSamples(): Flow<SensorData>
}