package com.jj.sensorcollector.domain.usecases.sensors

import com.jj.sensorcollector.domain.result.UseCaseResult
import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager

class StartGPSUseCase(private val globalSensorManager: IGlobalSensorManager) {

    operator fun invoke(): UseCaseResult<Unit> {
        return try {
            globalSensorManager.startGPS()
            UseCaseResult.Success(Unit)
        } catch (e: Exception) {
            UseCaseResult.Error(Unit, e)
        }
    }
}