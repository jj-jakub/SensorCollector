package com.jj.core.domain.sensors

import com.jj.core.domain.result.UseCaseResult
import com.jj.core.domain.sensors.IGlobalSensorManager

class StartAccelerometerUseCase(private val globalSensorManager: IGlobalSensorManager) {

    suspend operator fun invoke(): UseCaseResult<Unit> {
        return try {
            globalSensorManager.startAccelerometer()
            UseCaseResult.Success(Unit)
        } catch (e: Exception) {
            UseCaseResult.Error(Unit, e)
        }
    }
}