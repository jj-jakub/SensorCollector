package com.jj.hardware.di

import com.jj.domain.hardware.accelerometer.manager.AccelerometerManager
import com.jj.domain.hardware.gps.manager.GPSManager
import com.jj.domain.hardware.gyroscope.manager.GyroscopeManager
import com.jj.domain.hardware.magneticfield.manager.MagneticFieldManager
import com.jj.domain.hardware.vibration.managers.VibrationManager
import com.jj.domain.monitoring.AccelerometerStateMonitor
import com.jj.domain.monitoring.GPSStateMonitor
import com.jj.domain.monitoring.GyroscopeStateMonitor
import com.jj.domain.monitoring.MagneticFieldStateMonitor
import com.jj.domain.monitoring.SystemStateMonitor
import com.jj.hardware.data.hardware.accelerometer.manager.AndroidAccelerometerManager
import com.jj.hardware.data.hardware.gps.manager.AndroidGPSManager
import com.jj.hardware.data.hardware.gyroscope.manager.AndroidGyroscopeManager
import com.jj.hardware.data.hardware.magneticfield.manager.AndroidMagneticFieldManager
import com.jj.hardware.data.hardware.vibration.AndroidVibrationManager
import com.jj.hardware.data.monitoring.DefaultAccelerometerStateMonitor
import com.jj.hardware.data.monitoring.DefaultGPSStateMonitor
import com.jj.hardware.data.monitoring.DefaultGyroscopeStateMonitor
import com.jj.hardware.data.monitoring.DefaultMagneticFieldStateMonitor
import com.jj.hardware.data.monitoring.DefaultSystemStateMonitor
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
        AndroidGyroscopeManager(
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
    single<VibrationManager> {
        AndroidVibrationManager(
            context = get()
        )
    }
    single<AccelerometerStateMonitor> {
        DefaultAccelerometerStateMonitor(
            sensorsRepository = get(),
            accelerometerManager = get(),
            timeProvider = get(),
            coroutineScopeProvider = get()
        )
    }
    single<GyroscopeStateMonitor> {
        DefaultGyroscopeStateMonitor(
            gyroscopeManager = get(),
            timeProvider = get(),
            coroutineScopeProvider = get()
        )
    }
    single<MagneticFieldStateMonitor> {
        DefaultMagneticFieldStateMonitor(
            magneticFieldManager = get(),
            timeProvider = get(),
            coroutineScopeProvider = get()
        )
    }
    single<GPSStateMonitor> {
        DefaultGPSStateMonitor(
            gpsRepository = get(),
            gpsManager = get(),
            timeProvider = get(),
            coroutineScopeProvider = get()
        )
    }
    single<SystemStateMonitor> {
        DefaultSystemStateMonitor(
            accelerometerStateMonitor = get(),
            gyroscopeStateMonitor = get(),
            magneticFieldStateMonitor = get(),
            gpsStateMonitor = get()
        )
    }
}