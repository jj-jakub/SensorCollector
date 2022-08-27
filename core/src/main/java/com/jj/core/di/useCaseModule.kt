package com.jj.core.di

import com.jj.domain.hardware.accelerometer.usecase.StartAccelerometerAnalysis
import com.jj.domain.hardware.accelerometer.usecase.StopAccelerometerAnalysis
import com.jj.domain.hardware.general.usecase.StartAnalysis
import com.jj.domain.hardware.gps.usecase.StartGPSAnalysis
import com.jj.domain.hardware.gps.usecase.StopGPSAnalysis
import com.jj.domain.hardware.gps.usecase.path.FinishPath
import com.jj.domain.hardware.gps.usecase.path.StartPath
import com.jj.domain.hardware.gyroscope.usecase.StartGyroscopeCollection
import com.jj.domain.hardware.magneticfield.usecase.StartMagneticFieldCollection
import com.jj.domain.travel.usecase.ClearAllTravelItems
import com.jj.domain.travel.usecase.DeleteTravelItem
import com.jj.domain.travel.usecase.GetAllTravelItems
import com.jj.domain.travel.usecase.GetTravelItemsForList
import com.jj.domain.travel.usecase.SaveTravelItem
import org.koin.dsl.module

val useCaseModule = module {

    single { StartAccelerometerAnalysis(analysisStarter = get()) }
    single { StopAccelerometerAnalysis(analysisStarter = get()) }

    single { StartGPSAnalysis(gpsSampleAnalyser = get()) }
    single { StopGPSAnalysis(gpsSampleAnalyser = get()) }

    single {
        StartPath(
            pathRepository = get(), timeProvider = get()
        )
    }
    single {
        FinishPath(
            pathRepository = get(), timeProvider = get()
        )
    }

    single { StartAnalysis(analysisStarter = get()) }

    single { StartGyroscopeCollection(sensorsRepository = get()) }

    single { StartMagneticFieldCollection(sensorsRepository = get()) }

    single { GetAllTravelItems(travelRepository = get()) }
    single { GetTravelItemsForList(travelRepository = get()) }
    single { SaveTravelItem(travelRepository = get()) }
    single { ClearAllTravelItems(travelRepository = get()) }
    single { DeleteTravelItem(travelRepository = get()) }
}