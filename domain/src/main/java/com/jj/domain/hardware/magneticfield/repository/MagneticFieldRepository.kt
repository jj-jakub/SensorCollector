package com.jj.domain.hardware.magneticfield.repository

import com.jj.domain.hardware.model.SensorData
import kotlinx.coroutines.flow.Flow

interface MagneticFieldRepository {

    fun collectMagneticFieldSamples(): Flow<SensorData>
}