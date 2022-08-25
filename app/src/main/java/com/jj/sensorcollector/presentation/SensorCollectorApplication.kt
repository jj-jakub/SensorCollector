package com.jj.sensorcollector.presentation

import android.app.Application
import com.jj.sensorcollector.di.KoinLauncher
import com.jj.domain.base.initializer.ProgramInitializer
import org.koin.android.ext.android.inject

class SensorCollectorApplication : Application() {

    private val koinLauncher: KoinLauncher = KoinLauncher()

    private val programInitializer: ProgramInitializer by inject()

    override fun onCreate() {
        super.onCreate()
        koinLauncher.startKoin(this)
        programInitializer.initialize()
    }
}