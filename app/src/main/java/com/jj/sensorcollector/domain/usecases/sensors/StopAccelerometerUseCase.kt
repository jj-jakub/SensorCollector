package com.jj.sensorcollector.domain.usecases.sensors

import com.jj.sensorcollector.domain.result.UseCaseResult
import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager

class StopAccelerometerUseCase(private val globalSensorManager: IGlobalSensorManager) {

    operator fun invoke(): UseCaseResult<Unit> {
        return try {
            globalSensorManager.stopAccelerometer()
            UseCaseResult.Success(Unit)
        } catch (e: Exception) {
            UseCaseResult.Error(Unit, e)
        }
    }
}