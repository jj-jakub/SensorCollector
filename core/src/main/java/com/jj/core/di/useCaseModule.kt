package com.jj.core.di

import com.jj.core.domain.sensors.SensorUseCases
import com.jj.core.domain.sensors.StartAccelerometerUseCase
import com.jj.core.domain.sensors.StartGPSUseCase
import com.jj.core.domain.sensors.StopAccelerometerUseCase
import com.jj.core.domain.sensors.StopGPSUseCase
import org.koin.dsl.module

val useCaseModule = module {

    single { StartAccelerometerUseCase(get()) }
    single { StopAccelerometerUseCase(get()) }

    single { StartGPSUseCase(get()) }
    single { StopGPSUseCase(get()) }

    single { SensorUseCases(get(), get(), get(), get()) }
}