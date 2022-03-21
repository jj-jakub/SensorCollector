package com.jj.sensorcollector.di.koin

import com.jj.sensorcollector.domain.usecases.sensors.SensorUseCases
import com.jj.sensorcollector.domain.usecases.sensors.StartAccelerometerUseCase
import com.jj.sensorcollector.domain.usecases.sensors.StartGPSUseCase
import com.jj.sensorcollector.domain.usecases.sensors.StopAccelerometerUseCase
import com.jj.sensorcollector.domain.usecases.sensors.StopGPSUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { StartAccelerometerUseCase(get()) }
    single { StopAccelerometerUseCase(get()) }

    single { StartGPSUseCase(get()) }
    single { StopGPSUseCase(get()) }

    single { SensorUseCases(get(), get(), get(), get()) }
}