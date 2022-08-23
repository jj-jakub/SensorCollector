package com.jj.core.data.repository

import com.jj.domain.sensors.model.SensorData
import com.jj.core.domain.managers.MagneticFieldManager
import com.jj.core.domain.repository.MagneticFieldRepository
import kotlinx.coroutines.flow.Flow

class DefaultMagneticFieldRepository(
    private val magneticFieldManager: MagneticFieldManager
) : MagneticFieldRepository {

    override fun collectMagneticFieldSamples(): Flow<SensorData> = magneticFieldManager.collectRawSensorSamples()
}