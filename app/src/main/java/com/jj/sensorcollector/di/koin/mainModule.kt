package com.jj.sensorcollector.di.koin

import android.text.Spannable
import androidx.room.Room
import com.jj.sensorcollector.data.GlobalEventsCollector
import com.jj.sensorcollector.data.csv.DefaultCSVFileCreator
import com.jj.sensorcollector.data.database.SamplesDatabase
import com.jj.sensorcollector.data.network.RetrofitFactory
import com.jj.sensorcollector.data.repository.DefaultGlobalEventRepository
import com.jj.sensorcollector.data.repository.DefaultSamplesRepository
import com.jj.sensorcollector.data.sensors.AccelerometerDataCollector
import com.jj.sensorcollector.data.sensors.GPSDataCollector
import com.jj.sensorcollector.data.sensors.GlobalSensorCollector
import com.jj.sensorcollector.data.sensors.GlobalSensorManager
import com.jj.sensorcollector.data.text.VersionTextProvider
import com.jj.sensorcollector.domain.csv.CSVFileCreator
import com.jj.sensorcollector.domain.events.EventsCollector
import com.jj.sensorcollector.domain.events.GlobalEventsRepository
import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager
import com.jj.sensorcollector.domain.sensors.SamplesRepository
import com.jj.sensorcollector.domain.sensors.interfaces.AccelerometerManager
import com.jj.sensorcollector.domain.sensors.interfaces.GPSManager
import com.jj.sensorcollector.framework.notification.NotificationManagerBuilder
import com.jj.sensorcollector.framework.sensors.AndroidAccelerometerManager
import com.jj.sensorcollector.framework.sensors.AndroidGPSManager
import com.jj.sensorcollector.playground1.framework.data.managers.AndroidAnalyzerStarter
import com.jj.sensorcollector.playground1.data.repository.DefaultAccelerometerRepository
import com.jj.sensorcollector.playground1.data.Initializator
import com.jj.sensorcollector.playground1.data.AccelerometerSampleAnalyzer
import com.jj.sensorcollector.playground1.data.samples.accelerometer.AccelerometerThresholdAnalyzer
import com.jj.sensorcollector.playground1.data.api.DefaultAccelerometerAPI
import com.jj.sensorcollector.playground1.data.database.AnalysedSamplesDatabase
import com.jj.sensorcollector.playground1.data.managers.DefaultScreenStateCollector
import com.jj.sensorcollector.playground1.data.managers.DefaultSoundManager
import com.jj.sensorcollector.playground1.data.repository.DefaultGPSRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultGyroscopeRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultMagneticFieldRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultSensorsRepository
import com.jj.sensorcollector.playground1.data.samples.gps.DefaultGPSSampleAnalyzer
import com.jj.sensorcollector.playground1.data.time.DefaultTimeProvider
import com.jj.sensorcollector.playground1.domain.managers.AnalyzerStarter
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.api.AccelerometerAPI
import com.jj.sensorcollector.playground1.domain.managers.ScreenStateCollector
import com.jj.sensorcollector.playground1.domain.managers.SoundManager
import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.repository.GyroscopeRepository
import com.jj.sensorcollector.playground1.domain.repository.MagneticFieldRepository
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.samples.accelerometer.AccThresholdAnalyzer
import com.jj.sensorcollector.playground1.domain.samples.gps.GPSSampleAnalyzer
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import com.jj.sensorcollector.playground1.domain.ui.text.TextCreator
import com.jj.sensorcollector.playground1.framework.data.managers.AndroidGyroscopeManager
import com.jj.sensorcollector.playground1.framework.data.managers.AndroidMagneticFieldManager
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidTextCreator
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single { Room.databaseBuilder(androidContext(), SamplesDatabase::class.java, "samples_database.db").build() }
    single { Room.databaseBuilder(androidContext(), AnalysedSamplesDatabase::class.java, "analysed_samples_database.db").build() }

    single { RetrofitFactory() }
    single { VersionTextProvider() }

    single { AccelerometerDataCollector() }
    single { GPSDataCollector() }

    single { GlobalSensorCollector(get(), get(), get()) }
    single { NotificationManagerBuilder() }

    single<EventsCollector> { GlobalEventsCollector(get()) }
    single<CSVFileCreator> { DefaultCSVFileCreator(androidContext()) }

    single<SamplesRepository> { DefaultSamplesRepository(get<SamplesDatabase>().gpsDataDao, get<SamplesDatabase>().accelerationDataDao) }
    single<GlobalEventsRepository> { DefaultGlobalEventRepository(get<SamplesDatabase>().globalEventDataDao) }

    single<IGlobalSensorManager> { GlobalSensorManager(get(), get(), get()) }
    single<AccelerometerManager> { AndroidAccelerometerManager(androidContext(), get()) }
    single<GPSManager> { AndroidGPSManager(androidContext(), get()) }

    single<com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager> {
        com.jj.sensorcollector.playground1.framework.data.managers.AndroidAccelerometerManager(
            androidContext()
        )
    }

    single<com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager> {
        AndroidGyroscopeManager(
            androidContext()
        )
    }

    single<com.jj.sensorcollector.playground1.domain.managers.MagneticFieldManager> {
        AndroidMagneticFieldManager(
            androidContext()
        )
    }

    single<com.jj.sensorcollector.playground1.domain.managers.GPSManager> {
        com.jj.sensorcollector.playground1.framework.data.managers.AndroidGPSManager(
            androidContext()
        )
    }

    single<TimeProvider> { DefaultTimeProvider() }
    single<AccThresholdAnalyzer> { AccelerometerThresholdAnalyzer(get()) }
    single<AccelerometerRepository> {
        DefaultAccelerometerRepository(
            accelerometerManager = get(),
            accelerometerAPI = get(),
            analysedAccelerometerSampleDao = get<AnalysedSamplesDatabase>().analysedAccelerometerSampleDao
        )
    }
    single<GyroscopeRepository> { DefaultGyroscopeRepository(get()) }
    single<MagneticFieldRepository> { DefaultMagneticFieldRepository(get()) }

    single<GPSRepository> {
        DefaultGPSRepository(
            gpsManager = get(),
            analysedGPSSampleDao = get<AnalysedSamplesDatabase>().analysedGPSSampleDao
        )
    }
    single<SensorsRepository> { DefaultSensorsRepository(get(), get(), get()) }
    single { AccelerometerSampleAnalyzer(get(), get(), get()) }
    single<GPSSampleAnalyzer> { DefaultGPSSampleAnalyzer(get(), get()) }
    single<AnalyzerStarter> { AndroidAnalyzerStarter(get()) }
    single<ScreenStateCollector> { DefaultScreenStateCollector() }
    single<SoundManager> { DefaultSoundManager() }
    single { Initializator(get(), get(), get()) }

    single<AccelerometerAPI> { DefaultAccelerometerAPI() }

    single<TextCreator<Spannable>> { AndroidTextCreator() }
    viewModel { SensorsDataViewModel(get(), get(), get()) }
}