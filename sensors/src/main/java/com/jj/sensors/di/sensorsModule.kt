package com.jj.sensors.di

import com.jj.core.domain.managers.GyroscopeManager
import com.jj.core.domain.managers.MagneticFieldManager
import com.jj.core.domain.sensors.interfaces.AccelerometerManager
import com.jj.core.domain.sensors.interfaces.GPSManager
import com.jj.sensors.framework.managers.AndroidAccelerometerManager
import com.jj.sensors.framework.managers.AndroidGPSManager
import com.jj.sensors.framework.managers.AndroidMagneticFieldManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val sensorsModule = module {

    single<AccelerometerManager> {
        AndroidAccelerometerManager(
            context = androidContext(),
            coroutineScopeProvider = get()
        )
    }
    single<GPSManager> {
        AndroidGPSManager(
            context = androidContext(),
            coroutineScopeProvider = get()
        )
    }

    single<GyroscopeManager> {
        com.jj.sensors.framework.managers.AndroidGyroscopeManager(
            context = androidContext(),
            coroutineScopeProvider = get()
        )
    }

    single<MagneticFieldManager> {
        AndroidMagneticFieldManager(
            context = androidContext(),
            coroutineScopeProvider = get()
        )
    }
}