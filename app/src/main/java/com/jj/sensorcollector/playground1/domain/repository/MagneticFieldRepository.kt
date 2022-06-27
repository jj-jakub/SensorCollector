package com.jj.sensorcollector.playground1.domain.repository

import com.jj.sensors.domain.samples.SensorData
import kotlinx.coroutines.flow.Flow

interface MagneticFieldRepository {

    fun collectMagneticFieldSamples(): Flow<SensorData>
}