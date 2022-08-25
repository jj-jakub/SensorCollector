package com.jj.sensorcollector.di

import com.jj.sensorcollector.data.base.initializer.DefaultProgramInitializer
import com.jj.domain.base.initializer.ProgramInitializer
import org.koin.dsl.module

val mainModule = module {

    single<ProgramInitializer> {
        DefaultProgramInitializer(
            csvFileCreator = get(),
            defaultAccelerometerSampleAnalyser = get(),
            analysisStarter = get(),
            serverStarter = get(),
            systemStateMonitor = get(),
            gpsPathAnalyser = get(),
            coroutineScopeProvider = get()
        )
    }
}