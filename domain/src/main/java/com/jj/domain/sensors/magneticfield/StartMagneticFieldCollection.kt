package com.jj.domain.sensors.magneticfield

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.sensors.model.SensorData
import com.jj.domain.sensors.general.SensorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StartMagneticFieldCollection(
    private val sensorsRepository: SensorsRepository,
) : UseCase<Unit, Flow<SensorData.MagneticFieldSample>> {

    override suspend fun invoke(param: Unit): Flow<SensorData.MagneticFieldSample> = flow {
        sensorsRepository.collectMagneticFieldSamples().collect {
            if (it is SensorData.MagneticFieldSample) emit(it)
        }
    }
}