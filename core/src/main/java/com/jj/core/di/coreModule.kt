package com.jj.core.di

import androidx.compose.ui.text.AnnotatedString
import androidx.room.Room
import com.jj.core.data.AccelerometerSampleAnalyzer
import com.jj.core.data.api.DefaultAccelerometerAPI
import com.jj.core.data.csv.DefaultCSVFileCreator
import com.jj.core.data.database.AnalysedSamplesDatabase
import com.jj.core.data.database.SamplesDatabase
import com.jj.core.data.managers.DefaultRemoteControlManager
import com.jj.core.data.repository.DefaultAccelerometerRepository
import com.jj.core.data.repository.DefaultGPSRepository
import com.jj.core.data.repository.DefaultGlobalEventRepository
import com.jj.core.data.repository.DefaultGyroscopeRepository
import com.jj.core.data.repository.DefaultMagneticFieldRepository
import com.jj.core.data.repository.DefaultPathRepository
import com.jj.core.data.repository.DefaultSamplesRepository
import com.jj.core.data.repository.DefaultSensorsRepository
import com.jj.core.data.repository.GlobalEventsCollector
import com.jj.core.data.samples.accelerometer.AccelerometerThresholdAnalyzer
import com.jj.core.data.samples.gps.DefaultGPSPathAnalyser
import com.jj.core.data.samples.gps.DefaultGPSSampleAnalyzer
import com.jj.core.data.samples.gps.DefaultGPSVelocityCalculator
import com.jj.core.data.sensors.AccelerometerDataCollector
import com.jj.core.data.sensors.GPSDataCollector
import com.jj.core.data.sensors.GlobalSensorCollector
import com.jj.core.data.sensors.GlobalSensorManager
import com.jj.core.data.text.VersionTextProvider
import com.jj.core.data.time.DefaultTimeProvider
import com.jj.core.data.travel.DefaultTravelRepository
import com.jj.core.data.travel.database.TravelDatabase
import com.jj.core.domain.api.AccelerometerAPI
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.csv.CSVFileCreator
import com.jj.core.domain.events.EventsCollector
import com.jj.core.domain.events.GlobalEventsRepository
import com.jj.core.domain.gps.GPSPathAnalyser
import com.jj.core.domain.gps.GPSVelocityCalculator
import com.jj.core.domain.managers.AnalyzerStarter
import com.jj.core.domain.managers.CameraManager
import com.jj.core.domain.repository.AccelerometerRepository
import com.jj.core.domain.repository.GPSRepository
import com.jj.core.domain.repository.GyroscopeRepository
import com.jj.core.domain.repository.MagneticFieldRepository
import com.jj.core.domain.repository.PathRepository
import com.jj.core.domain.repository.SensorsRepository
import com.jj.core.domain.repository.TravelRepository
import com.jj.core.domain.samples.accelerometer.AccThresholdAnalyzer
import com.jj.core.domain.samples.samples.gps.GPSSampleAnalyzer
import com.jj.core.domain.sensors.IGlobalSensorManager
import com.jj.core.domain.sensors.SamplesRepository
import com.jj.core.domain.server.RemoteControlManager
import com.jj.core.domain.time.TimeProvider
import com.jj.core.domain.travel.ClearAllTravelItems
import com.jj.core.domain.travel.DeleteTravelItem
import com.jj.core.domain.travel.GetAllTravelItems
import com.jj.core.domain.travel.GetTravelItemsForList
import com.jj.core.domain.travel.SaveTravelItem
import com.jj.core.domain.ui.text.TextCreator
import com.jj.core.framework.domain.managers.AndroidAnalyzerStarter
import com.jj.core.framework.managers.AndroidCameraManager
import com.jj.core.framework.managers.CameraXProvider
import com.jj.core.framework.notification.NotificationManagerBuilder
import com.jj.core.framework.presentation.camera.CameraScreenViewModel
import com.jj.core.framework.presentation.sensors.SensorsDataViewModel
import com.jj.core.framework.presentation.settings.SettingsScreenViewModel
import com.jj.core.framework.presentation.travel.TravelScreenViewModel
import com.jj.core.framework.presentation.uiplayground.UIPlaygroundScreenViewModel
import com.jj.core.framework.text.ComposeTextCreator
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {

    single { Room.databaseBuilder(androidContext(), SamplesDatabase::class.java, "samples_database.db").build() }
    single { Room.databaseBuilder(androidContext(), AnalysedSamplesDatabase::class.java, "analysed_samples_database.db").build() }
    single { Room.databaseBuilder(androidContext(), TravelDatabase::class.java, "travel_database.db").build() }

    single<SamplesRepository> { DefaultSamplesRepository(get<SamplesDatabase>().gpsDataDao, get<SamplesDatabase>().accelerationDataDao) }
    single<GlobalEventsRepository> { DefaultGlobalEventRepository(get<SamplesDatabase>().globalEventDataDao) }

    single { VersionTextProvider() }
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
    single<TravelRepository> { DefaultTravelRepository(get<TravelDatabase>().travelItemDataDao) }

    single { AccelerometerSampleAnalyzer(get(), get(), get(), get()) }
    single<GPSSampleAnalyzer> { DefaultGPSSampleAnalyzer(get(), get(), get()) }
    single<AnalyzerStarter> { AndroidAnalyzerStarter(get()) }

    single<AccelerometerAPI> { DefaultAccelerometerAPI() }

    single { AccelerometerDataCollector() }
    single { GPSDataCollector() }

    single { GlobalSensorCollector(get(), get(), get(), get()) }
    single { NotificationManagerBuilder() }

    single<CoroutineScopeProvider> { com.jj.core.data.coroutines.DefaultCoroutineScopeProvider() }

    single<EventsCollector> { GlobalEventsCollector(get(), get()) }

    single<IGlobalSensorManager> { GlobalSensorManager(get(), get(), get()) }

//    single<TextCreator<Spannable>> { AndroidTextCreator() }
    single<TextCreator<AnnotatedString>> { ComposeTextCreator() }
    viewModel { SensorsDataViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { SettingsScreenViewModel(get()) }
    viewModel { CameraScreenViewModel(get()) }
    viewModel { TravelScreenViewModel(get(), get(), get()) }
    viewModel { UIPlaygroundScreenViewModel() }

    single<RemoteControlManager> { DefaultRemoteControlManager(get(), get()) }

    single { CameraXProvider(androidContext()) }
    single<CameraManager> { AndroidCameraManager(androidContext(), get()) }

    single<GPSPathAnalyser> {
        DefaultGPSPathAnalyser(
            gpsRepository = get(),
            pathRepository = get(),
            gpsVelocityCalculator = get(),
            coroutineScopeProvider = get()
        )
    }

    single<CSVFileCreator> { DefaultCSVFileCreator(androidContext()) }

    single<GPSVelocityCalculator> { DefaultGPSVelocityCalculator() }

    single { GetAllTravelItems(get()) }
    single { GetTravelItemsForList(get()) }
    single { SaveTravelItem(get()) }
    single { ClearAllTravelItems(get()) }
    single { DeleteTravelItem(get()) }
}
