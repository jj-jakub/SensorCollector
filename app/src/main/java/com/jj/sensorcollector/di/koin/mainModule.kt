package com.jj.sensorcollector.di.koin

import androidx.room.Room
import com.jj.sensorcollector.data.GlobalEventsCollector
import com.jj.sensorcollector.data.database.SamplesDatabase
import com.jj.sensorcollector.data.network.RetrofitFactory
import com.jj.sensorcollector.data.repository.DefaultGlobalEventRepository
import com.jj.sensorcollector.data.repository.DefaultSamplesRepository
import com.jj.sensorcollector.data.sensors.AccelerometerDataCollector
import com.jj.sensorcollector.data.sensors.GPSDataCollector
import com.jj.sensorcollector.data.sensors.GlobalSensorCollector
import com.jj.sensorcollector.data.sensors.GlobalSensorManager
import com.jj.sensorcollector.data.text.VersionTextProvider
import com.jj.sensorcollector.domain.events.EventsCollector
import com.jj.sensorcollector.domain.events.GlobalEventsRepository
import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager
import com.jj.sensorcollector.domain.sensors.SamplesRepository
import com.jj.sensorcollector.domain.sensors.interfaces.AccelerometerManager
import com.jj.sensorcollector.domain.sensors.interfaces.GPSManager
import com.jj.sensorcollector.framework.notification.NotificationManagerBuilder
import com.jj.sensorcollector.framework.sensors.AndroidAccelerometerManager
import com.jj.sensorcollector.framework.sensors.AndroidGPSManager
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {

    single { Room.databaseBuilder(androidContext(), SamplesDatabase::class.java, "samples_database.db").build() }

    single { RetrofitFactory() }
    single { VersionTextProvider() }

    single { AccelerometerDataCollector() }
    single { GPSDataCollector() }

    single { GlobalSensorCollector(get(), get(), get()) }
    single { NotificationManagerBuilder() }

    single<EventsCollector> { GlobalEventsCollector(get()) }

    single<SamplesRepository> { DefaultSamplesRepository(get<SamplesDatabase>().gpsDataDao, get<SamplesDatabase>().accelerationDataDao) }
    single<GlobalEventsRepository> { DefaultGlobalEventRepository(get<SamplesDatabase>().globalEventDataDao) }

    single<IGlobalSensorManager> { GlobalSensorManager(get(), get(), get()) }
    single<AccelerometerManager> { AndroidAccelerometerManager(androidContext(), get()) }
    single<GPSManager> { AndroidGPSManager(androidContext(), get()) }
}