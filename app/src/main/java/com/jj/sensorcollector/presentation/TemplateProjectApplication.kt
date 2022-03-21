package com.jj.sensorcollector.presentation

import android.app.Application
import com.jj.sensorcollector.data.sensors.GlobalSensorCollector
import com.jj.sensorcollector.di.koin.KoinLauncher
import com.jj.sensorcollector.framework.services.CollectingDataService
import org.koin.android.ext.android.inject

class SensorCollectorApplication : Application() {

    private val koinLauncher = KoinLauncher()
    private val globalSensorCollector: GlobalSensorCollector by inject()

    override fun onCreate() {
        super.onCreate()
        koinLauncher.startKoin(this)
        globalSensorCollector.ping()
        CollectingDataService.startCollectingGPS(this)
    }
}