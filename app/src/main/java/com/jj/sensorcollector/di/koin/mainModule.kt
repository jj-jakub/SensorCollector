package com.jj.sensorcollector.di.koin

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
import com.jj.sensorcollector.playground1.data.repository.DefaultAccelerometerRepository
import com.jj.sensorcollector.playground1.data.Initializator
import com.jj.sensorcollector.playground1.data.SampleAnalyzer
import com.jj.sensorcollector.playground1.data.accanalyzers.AccAnalyzer1
import com.jj.sensorcollector.playground1.data.accanalyzers.AccAnalyzer2
import com.jj.sensorcollector.playground1.data.accanalyzers.AccAnalyzer3
import com.jj.sensorcollector.playground1.data.api.DefaultAccelerometerService
import com.jj.sensorcollector.playground1.data.dummymanagers.DefaultSampleXAnalyzer
import com.jj.sensorcollector.playground1.data.dummymanagers.DefaultScreenStateCollector
import com.jj.sensorcollector.playground1.data.dummymanagers.DefaultSoundManager
import com.jj.sensorcollector.playground1.data.repository.DefaultGyroscopeRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultMagneticFieldRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultSensorsRepository
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.api.AccelerometerService
import com.jj.sensorcollector.playground1.domain.managers.SampleXAnalyzer
import com.jj.sensorcollector.playground1.domain.managers.ScreenStateCollector
import com.jj.sensorcollector.playground1.domain.managers.SoundManager
import com.jj.sensorcollector.playground1.domain.repository.GyroscopeRepository
import com.jj.sensorcollector.playground1.domain.repository.MagneticFieldRepository
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
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
    single<CSVFileCreator> { DefaultCSVFileCreator(androidContext()) }

    single<SamplesRepository> { DefaultSamplesRepository(get<SamplesDatabase>().gpsDataDao, get<SamplesDatabase>().accelerationDataDao) }
    single<GlobalEventsRepository> { DefaultGlobalEventRepository(get<SamplesDatabase>().globalEventDataDao) }

    single<IGlobalSensorManager> { GlobalSensorManager(get(), get(), get()) }
    single<AccelerometerManager> { AndroidAccelerometerManager(androidContext(), get()) }
    single<GPSManager> { AndroidGPSManager(androidContext(), get()) }

    single<com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager> {
        com.jj.sensorcollector.playground1.framework.data.AndroidAccelerometerManager(
            androidContext()
        )
    }

    single<com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager> {
        com.jj.sensorcollector.playground1.framework.data.AndroidGyroscopeManager(
            androidContext()
        )
    }

    single<com.jj.sensorcollector.playground1.domain.managers.MagneticFieldManager> {
        com.jj.sensorcollector.playground1.framework.data.AndroidMagneticFieldManager(
            androidContext()
        )
    }

    single<AccelerometerRepository> { DefaultAccelerometerRepository(get(), get()) }
    single<GyroscopeRepository> { DefaultGyroscopeRepository(get()) }
    single<MagneticFieldRepository> { DefaultMagneticFieldRepository(get()) }

    single<SensorsRepository> { DefaultSensorsRepository(get(), get(), get()) }
    single { SampleAnalyzer(get(), AccAnalyzer1(), AccAnalyzer2(), AccAnalyzer3()) }

    single<SampleXAnalyzer> { DefaultSampleXAnalyzer(get()) }
    single<ScreenStateCollector> { DefaultScreenStateCollector() }
    single<SoundManager> { DefaultSoundManager() }
    single { Initializator(get(), get(), get()) }

    single<AccelerometerService> { DefaultAccelerometerService() }

    viewModel { SensorsDataViewModel(get()) }
}