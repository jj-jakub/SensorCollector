package com.jj.domain.hardware.gyroscope.usecase

import com.jj.domain.base.usecase.UseCase
import com.jj.domain.hardware.model.SensorData
import com.jj.domain.hardware.general.SensorsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class StartGyroscopeCollection(
    private val sensorsRepository: SensorsRepository,
) : UseCase<Unit, Flow<SensorData.GyroscopeSample>> {

    override suspend fun invoke(param: Unit): Flow<SensorData.GyroscopeSample> = flow {
        sensorsRepository.collectGyroscopeSamples().collect {
            if (it is SensorData.GyroscopeSample) emit(it)
        }
    }
}