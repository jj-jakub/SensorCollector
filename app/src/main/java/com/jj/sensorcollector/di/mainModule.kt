package com.jj.sensorcollector.di

import com.jj.sensorcollector.data.initializers.DefaultAppInitializer
import com.jj.sensorcollector.domain.initializers.AppInitializer
import org.koin.dsl.module

val mainModule = module {

    single<AppInitializer> {
        DefaultAppInitializer(
            csvFileCreator = get(),
            accelerometerSampleAnalyser = get(),
            analysisStarter = get(),
            serverStarter = get(),
            systemStateMonitor = get(),
            gpsPathAnalyser = get(),
            coroutineScopeProvider = get()
        )
    }
}