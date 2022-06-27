package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensors.domain.samples.SensorData
import com.jj.sensors.domain.managers.MagneticFieldManager
import com.jj.sensorcollector.playground1.domain.repository.MagneticFieldRepository
import kotlinx.coroutines.flow.Flow

class DefaultMagneticFieldRepository(
    private val magneticFieldManager: MagneticFieldManager
) : MagneticFieldRepository {

    override fun collectMagneticFieldSamples(): Flow<SensorData> = magneticFieldManager.collectRawSensorSamples()
}