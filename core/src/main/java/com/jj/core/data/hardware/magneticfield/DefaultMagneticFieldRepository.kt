package com.jj.core.data.hardware.magneticfield

import com.jj.domain.hardware.model.SensorData
import com.jj.domain.hardware.magneticfield.manager.MagneticFieldManager
import com.jj.domain.hardware.magneticfield.repository.MagneticFieldRepository
import kotlinx.coroutines.flow.Flow

class DefaultMagneticFieldRepository(
    private val magneticFieldManager: MagneticFieldManager
) : MagneticFieldRepository {

    override fun collectMagneticFieldSamples(): Flow<SensorData> = magneticFieldManager.collectRawSensorSamples()
}