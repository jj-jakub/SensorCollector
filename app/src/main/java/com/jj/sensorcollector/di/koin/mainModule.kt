package com.jj.sensorcollector.di.koin

import com.jj.sensorcollector.data.network.RetrofitFactory
import com.jj.sensorcollector.data.sensors.AccelerometerDataCollector
import com.jj.sensorcollector.data.sensors.GPSDataCollector
import com.jj.sensorcollector.data.sensors.GlobalSensorCollector
import com.jj.sensorcollector.data.sensors.GlobalSensorManager
import com.jj.sensorcollector.data.text.VersionTextProvider
import com.jj.sensorcollector.domain.sensors.ISensorManager
import com.jj.sensorcollector.domain.sensors.SensorData.AccelerometerData
import com.jj.sensorcollector.domain.sensors.SensorData.GPSData
import com.jj.sensorcollector.framework.sensors.AccelerometerManager
import com.jj.sensorcollector.framework.sensors.GPSManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {

    single { RetrofitFactory() }
    single { VersionTextProvider() }

    single { AccelerometerDataCollector() }
    single { GPSDataCollector() }

    single { GlobalSensorCollector(get(), get()) }

    single { GlobalSensorManager(get(), get()) }
    single<ISensorManager<AccelerometerData>> { AccelerometerManager(androidContext(), get()) }
    single<ISensorManager<GPSData>> { GPSManager(androidContext(), get()) }
}