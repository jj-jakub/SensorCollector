package com.jj.sensors.di

import com.jj.core.domain.sensors.interfaces.AccelerometerManager
import com.jj.core.domain.sensors.interfaces.GPSManager
import com.jj.sensors.framework.managers.AndroidAccelerometerManager
import com.jj.sensors.framework.managers.AndroidGPSManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sensorsModule = module {
    single<AccelerometerManager> { AndroidAccelerometerManager(androidContext(), get()) }
    single<GPSManager> { AndroidGPSManager(androidContext(), get()) }
}