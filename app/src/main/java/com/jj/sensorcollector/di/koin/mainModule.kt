package com.jj.sensorcollector.di.koin

import com.jj.core.data.csv.DefaultCSVFileCreator
import com.jj.core.domain.csv.CSVFileCreator
import com.jj.sensorcollector.playground1.data.initializers.DefaultAppInitializer
import com.jj.sensorcollector.playground1.domain.initializers.AppInitializer
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {

    single<AppInitializer> {
        DefaultAppInitializer(
            globalSensorCollector = get(),
            samplesRepository = get(),
            globalEventsRepository = get(),
            csvFileCreator = get(),
            accelerometerSampleAnalyzer = get(),
            analyzerStarter = get(),
            serverStarter = get(),
            systemStateMonitor = get(),
            gpsPathAnalyser = get(),
            coroutineScopeProvider = get()
        )
    }

}