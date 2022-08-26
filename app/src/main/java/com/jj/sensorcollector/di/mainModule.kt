package com.jj.sensorcollector.di

import com.jj.domain.base.initializer.ProgramInitializer
import com.jj.sensorcollector.data.base.initializer.DefaultProgramInitializer
import org.koin.dsl.module

val mainModule = module {
    single<ProgramInitializer> {
        DefaultProgramInitializer(
            startAnalysis = get(),
            serverStarter = get(),
            systemStateMonitor = get(),
            gpsPathAnalyser = get(),
        )
    }
}