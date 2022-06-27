package com.jj.sensorcollector.di.koin

import android.content.Context
import com.jj.sensors.di.sensorsModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class KoinLauncher {

    fun startKoin(applicationContext: Context) {
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(applicationContext)
            modules(mainModule, useCaseModule, sensorsModule)
        }
    }
}
