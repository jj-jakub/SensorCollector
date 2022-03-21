package com.jj.sensorcollector.domain.usecases.sensors

import com.jj.sensorcollector.domain.result.UseCaseResult
import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager

class StopGPSUseCase(private val globalSensorManager: IGlobalSensorManager) {

    operator fun invoke(): UseCaseResult<Unit> {
        return try {
            globalSensorManager.stopGPS()
            UseCaseResult.Success(Unit)
        } catch (e: Exception) {
            UseCaseResult.Error(Unit, e)
        }
    }
}