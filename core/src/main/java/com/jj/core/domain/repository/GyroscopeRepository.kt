package com.jj.core.domain.repository

import com.jj.core.domain.samples.SensorData
import kotlinx.coroutines.flow.Flow

interface GyroscopeRepository {

    fun collectGyroscopeSamples(): Flow<SensorData>
}