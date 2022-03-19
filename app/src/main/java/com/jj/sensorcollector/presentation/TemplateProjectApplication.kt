package com.jj.sensorcollector.presentation

import android.app.Application
import com.jj.sensorcollector.di.koin.KoinLauncher

class SensorCollectorApplication : Application() {

    private val koinLauncher = KoinLauncher()

    override fun onCreate() {
        super.onCreate()
        koinLauncher.startKoin(this)
    }
}