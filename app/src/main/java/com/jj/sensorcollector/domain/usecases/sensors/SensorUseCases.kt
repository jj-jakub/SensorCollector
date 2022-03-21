package com.jj.sensorcollector.domain.usecases.sensors

data class SensorUseCases(
        val startAccelerometerUseCase: StartAccelerometerUseCase,
        val stopAccelerometerUseCase: StopAccelerometerUseCase,
        val startGPSUseCase: StartGPSUseCase,
        val stopGPSUseCase: StopGPSUseCase
)
