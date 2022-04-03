package com.jj.sensorcollector.playground1.data.repository

import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager
import com.jj.sensorcollector.playground1.domain.managers.MagneticFieldManager
import com.jj.sensorcollector.playground1.domain.repository.GyroscopeRepository
import com.jj.sensorcollector.playground1.domain.repository.MagneticFieldRepository
import kotlinx.coroutines.flow.Flow

class DefaultMagneticFieldRepository(
    private val magneticFieldManager: MagneticFieldManager
) : MagneticFieldRepository {

    override fun collectMagneticFieldSamples(): Flow<SensorData> = magneticFieldManager.collectSensorSamples()
}