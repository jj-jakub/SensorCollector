package com.jj.core.domain.repository

import com.jj.domain.sensors.model.SensorData
import kotlinx.coroutines.flow.Flow

interface MagneticFieldRepository {

    fun collectMagneticFieldSamples(): Flow<SensorData>
}