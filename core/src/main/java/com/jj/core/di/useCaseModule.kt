package com.jj.core.di

import com.jj.core.domain.sensors.SensorUseCases
import com.jj.core.domain.sensors.StartAccelerometerUseCase
import com.jj.core.domain.sensors.StartGPSUseCase
import com.jj.core.domain.sensors.StopAccelerometerUseCase
import com.jj.core.domain.sensors.StopGPSUseCase
import com.jj.domain.travel.usecase.ClearAllTravelItems
import com.jj.domain.travel.usecase.DeleteTravelItem
import com.jj.domain.travel.usecase.GetAllTravelItems
import com.jj.domain.travel.usecase.GetTravelItemsForList
import com.jj.domain.travel.usecase.SaveTravelItem
import org.koin.dsl.module

val useCaseModule = module {

    single { StartAccelerometerUseCase(get()) }
    single { StopAccelerometerUseCase(get()) }

    single { StartGPSUseCase(get()) }
    single { StopGPSUseCase(get()) }

    single { SensorUseCases(get(), get(), get(), get()) }

    single { GetAllTravelItems(get()) }
    single { GetTravelItemsForList(get()) }
    single { SaveTravelItem(get()) }
    single { ClearAllTravelItems(get()) }
    single { DeleteTravelItem(get()) }
}