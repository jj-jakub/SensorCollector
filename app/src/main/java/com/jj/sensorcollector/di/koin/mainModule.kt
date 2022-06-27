package com.jj.sensorcollector.di.koin

import androidx.compose.ui.text.AnnotatedString
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
import com.jj.sensorcollector.playground1.data.AccelerometerSampleAnalyzer
import com.jj.sensorcollector.playground1.data.samples.accelerometer.AccelerometerThresholdAnalyzer
import com.jj.sensorcollector.playground1.data.api.DefaultAccelerometerAPI
import com.jj.sensorcollector.playground1.data.database.AnalysedSamplesDatabase
import com.jj.sensorcollector.playground1.data.initializers.DefaultAppInitializer
import com.jj.sensorcollector.playground1.data.managers.DefaultScreenStateCollector
import com.jj.sensorcollector.playground1.data.managers.DefaultSoundManager
import com.jj.sensorcollector.playground1.data.monitors.DefaultAccelerometerStateMonitor
import com.jj.sensorcollector.playground1.data.monitors.DefaultGPSStateMonitor
import com.jj.sensorcollector.playground1.data.monitors.DefaultGyroscopeStateMonitor
import com.jj.sensorcollector.playground1.data.monitors.DefaultMagneticFieldStateMonitor
import com.jj.sensorcollector.playground1.data.monitors.DefaultSystemStateMonitor
import com.jj.sensorcollector.playground1.data.repository.DefaultGPSRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultGyroscopeRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultMagneticFieldRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultPathRepository
import com.jj.sensorcollector.playground1.data.repository.DefaultSensorsRepository
import com.jj.sensorcollector.playground1.data.samples.gps.DefaultGPSPathAnalyser
import com.jj.sensorcollector.playground1.data.samples.gps.DefaultGPSSampleAnalyzer
import com.jj.sensorcollector.playground1.data.samples.gps.DefaultGPSVelocityCalculator
import com.jj.sensorcollector.playground1.data.server.DefaultRequestDispatcher
import com.jj.sensorcollector.playground1.data.time.DefaultTimeProvider
import com.jj.sensors.domain.managers.AnalyzerStarter
import com.jj.sensorcollector.playground1.domain.repository.AccelerometerRepository
import com.jj.sensorcollector.playground1.domain.api.AccelerometerAPI
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.playground1.domain.initializers.AppInitializer
import com.jj.sensors.domain.managers.ScreenStateCollector
import com.jj.sensors.domain.managers.SoundManager
import com.jj.sensors.domain.managers.VibrationManager
import com.jj.sensorcollector.playground1.domain.monitors.SystemStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.markers.AccelerometerStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.markers.GPSStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.markers.GyroscopeStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.markers.MagneticFieldStateMonitor
import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.repository.GyroscopeRepository
import com.jj.sensorcollector.playground1.domain.repository.MagneticFieldRepository
import com.jj.sensorcollector.playground1.domain.repository.PathRepository
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensors.domain.samples.accelerometer.AccThresholdAnalyzer
import com.jj.sensors.domain.samples.gps.GPSPathAnalyser
import com.jj.sensors.domain.samples.gps.GPSSampleAnalyzer
import com.jj.sensors.domain.samples.gps.GPSVelocityCalculator
import com.jj.sensorcollector.playground1.domain.server.IPProvider
import com.jj.sensorcollector.playground1.domain.server.ServerStarter
import com.jj.sensorcollector.playground1.domain.server.requests.RequestDispatcher
import com.jj.sensorcollector.playground1.domain.server.requests.RequestReceiver
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import com.jj.sensorcollector.playground1.domain.ui.text.TextCreator
import com.jj.sensorcollector.playground1.framework.domain.managers.AndroidAnalyzerStarter
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import com.jj.sensorcollector.playground1.framework.server.AndroidIPProvider
import com.jj.sensorcollector.playground1.framework.server.KtorServerStarter
import com.jj.sensorcollector.playground1.framework.server.requests.KtorRequestReceiver
import com.jj.sensorcollector.playground1.framework.ui.text.ComposeTextCreator
import com.jj.sensors.domain.managers.GyroscopeManager
import com.jj.sensors.domain.managers.MagneticFieldManager
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    single<AppInitializer> {
        DefaultAppInitializer(
            globalSensorCollector = get(),
            samplesRepository = get(),
            globalEventsRepository = get(),
            csvFileCreator = get(),
            accelerometerSampleAnalyzer = get(),
            analyzerStarter = get(),
            serverStarter = get(),
            systemStateMonitor = get(),
            gpsPathAnalyser = get(),
            coroutineScopeProvider = get()
        )
    }

    single { Room.databaseBuilder(androidContext(), SamplesDatabase::class.java, "samples_database.db").build() }
    single { Room.databaseBuilder(androidContext(), AnalysedSamplesDatabase::class.java, "analysed_samples_database.db").build() }

    single { RetrofitFactory() }
    single { VersionTextProvider() }

    single { AccelerometerDataCollector() }
    single { GPSDataCollector() }

    single { GlobalSensorCollector(get(), get(), get(), get()) }
    single { NotificationManagerBuilder() }

    single<CoroutineScopeProvider> { com.jj.core.data.coroutines.DefaultCoroutineScopeProvider() }

    single<EventsCollector> { GlobalEventsCollector(get(), get()) }
    single<CSVFileCreator> { DefaultCSVFileCreator(androidContext()) }

    single<SamplesRepository> { DefaultSamplesRepository(get<SamplesDatabase>().gpsDataDao, get<SamplesDatabase>().accelerationDataDao) }
    single<GlobalEventsRepository> { DefaultGlobalEventRepository(get<SamplesDatabase>().globalEventDataDao) }

    single<IGlobalSensorManager> { GlobalSensorManager(get(), get(), get()) }
    single<AccelerometerManager> { AndroidAccelerometerManager(androidContext(), get()) }
    single<GPSManager> { AndroidGPSManager(androidContext(), get()) }

    single<com.jj.sensors.domain.managers.AccelerometerManager> {
        com.jj.sensors.framework.managers.AndroidAccelerometerManager(
            androidContext(),
            get()
        )
    }

    single<GyroscopeManager> {
        com.jj.sensors.framework.managers.AndroidGyroscopeManager(
            androidContext(),
            get()
        )
    }

    single<MagneticFieldManager> {
        com.jj.sensors.framework.managers.AndroidMagneticFieldManager(
            androidContext(),
            get()
        )
    }

    single<com.jj.sensors.domain.managers.GPSManager> {
        com.jj.sensors.framework.managers.AndroidGPSManager(
            androidContext(),
            get()
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
    single<PathRepository> { DefaultPathRepository() }

    single { AccelerometerSampleAnalyzer(get(), get(), get(), get()) }
    single<GPSSampleAnalyzer> { DefaultGPSSampleAnalyzer(get(), get(), get()) }
    single<AnalyzerStarter> { AndroidAnalyzerStarter(get()) }
    single<ScreenStateCollector> { DefaultScreenStateCollector() }
    single<SoundManager> { DefaultSoundManager() }

    single<AccelerometerAPI> { DefaultAccelerometerAPI() }

//    single<TextCreator<Spannable>> { AndroidTextCreator() }
    single<TextCreator<AnnotatedString>> { ComposeTextCreator() }
    viewModel { SensorsDataViewModel(get(), get(), get(), get(), get(), get()) }

    single<IPProvider> { AndroidIPProvider(androidContext()) }
    single<RequestDispatcher> { DefaultRequestDispatcher(get()) }
    single<RequestReceiver> { KtorRequestReceiver(get()) }
    single<ServerStarter> { KtorServerStarter(get(), get()) }

    single<VibrationManager> { com.jj.sensors.framework.managers.AndroidVibrationManager(get()) }

    single<AccelerometerStateMonitor> { DefaultAccelerometerStateMonitor(get(), get(), get(), get()) }
    single<GyroscopeStateMonitor> { DefaultGyroscopeStateMonitor(get(), get(), get(), get()) }
    single<MagneticFieldStateMonitor> { DefaultMagneticFieldStateMonitor(get(), get(), get(), get()) }
    single<GPSStateMonitor> { DefaultGPSStateMonitor(get(), get(), get(), get()) }
    single<SystemStateMonitor> { DefaultSystemStateMonitor(get(), get(), get(), get()) }

    single<GPSPathAnalyser> {
        DefaultGPSPathAnalyser(
            gpsRepository = get(),
            pathRepository = get(),
            gpsVelocityCalculator = get(),
            coroutineScopeProvider = get()
        )
    }

    single<GPSVelocityCalculator> { DefaultGPSVelocityCalculator() }
}