package com.jj.core.di

import androidx.compose.ui.text.AnnotatedString
import androidx.room.Room
import com.jj.core.data.api.DefaultAccelerometerAPI
import com.jj.core.data.csv.DefaultCSVFileCreator
import com.jj.core.data.database.AnalysedSamplesDatabase
import com.jj.core.data.database.SamplesDatabase
import com.jj.core.data.managers.DefaultRemoteControlManager
import com.jj.core.data.repository.DefaultAccelerometerRepository
import com.jj.core.data.repository.DefaultGPSRepository
import com.jj.core.data.repository.DefaultGyroscopeRepository
import com.jj.core.data.repository.DefaultMagneticFieldRepository
import com.jj.core.data.repository.DefaultPathRepository
import com.jj.core.data.repository.DefaultSensorsRepository
import com.jj.core.data.samples.accelerometer.AccelerometerThresholdAnalyser
import com.jj.core.data.samples.gps.DefaultGPSPathAnalyser
import com.jj.core.data.samples.gps.DefaultGPSSampleAnalyzer
import com.jj.core.data.samples.gps.DefaultGPSVelocityCalculator
import com.jj.core.data.sensors.accelerometer.AccelerometerSampleAnalyser
import com.jj.core.data.text.VersionTextProvider
import com.jj.core.data.time.DefaultTimeProvider
import com.jj.core.data.travel.database.TravelDatabase
import com.jj.core.data.travel.repository.DefaultTravelRepository
import com.jj.core.domain.api.AccelerometerAPI
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.csv.CSVFileCreator
import com.jj.core.domain.gps.GPSPathAnalyser
import com.jj.core.domain.gps.GPSSampleAnalyzer
import com.jj.core.domain.gps.GPSVelocityCalculator
import com.jj.core.domain.managers.CameraManager
import com.jj.core.domain.repository.AccelerometerRepository
import com.jj.core.domain.repository.GyroscopeRepository
import com.jj.core.domain.repository.MagneticFieldRepository
import com.jj.core.domain.repository.PathRepository
import com.jj.core.domain.server.RemoteControlManager
import com.jj.core.domain.time.TimeProvider
import com.jj.core.domain.ui.text.TextCreator
import com.jj.core.framework.domain.managers.AndroidAnalysisStarter
import com.jj.core.framework.managers.AndroidCameraManager
import com.jj.core.framework.managers.CameraXProvider
import com.jj.core.framework.notification.NotificationManagerBuilder
import com.jj.core.framework.presentation.camera.CameraScreenViewModel
import com.jj.core.framework.presentation.sensors.SensorsDataViewModel
import com.jj.core.framework.presentation.settings.SettingsScreenViewModel
import com.jj.core.framework.presentation.travel.TravelScreenViewModel
import com.jj.core.framework.presentation.uiplayground.UIPlaygroundScreenViewModel
import com.jj.core.framework.text.ComposeTextCreator
import com.jj.domain.samples.accelerometer.AccThresholdAnalyser
import com.jj.domain.sensors.general.AnalysisStarter
import com.jj.domain.sensors.general.SensorsRepository
import com.jj.domain.sensors.gps.repository.GPSRepository
import com.jj.domain.travel.repository.TravelRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {

    single { Room.databaseBuilder(androidContext(), SamplesDatabase::class.java, "samples_database.db").build() }
    single { Room.databaseBuilder(androidContext(), AnalysedSamplesDatabase::class.java, "analysed_samples_database.db").build() }
    single { Room.databaseBuilder(androidContext(), TravelDatabase::class.java, "travel_database.db").build() }


    single { VersionTextProvider() }
    single<TimeProvider> { DefaultTimeProvider() }
    single<AccThresholdAnalyser> { AccelerometerThresholdAnalyser(get()) }
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

    single { AccelerometerSampleAnalyser(get(), get(), get(), get()) }
    single<GPSSampleAnalyzer> { DefaultGPSSampleAnalyzer(get(), get(), get()) }
    single<AnalysisStarter> { AndroidAnalysisStarter(get()) }

    single<AccelerometerAPI> { DefaultAccelerometerAPI() }

    single { NotificationManagerBuilder() }

    single<CoroutineScopeProvider> { com.jj.core.data.coroutines.DefaultCoroutineScopeProvider() }

    single<TextCreator<AnnotatedString>> { ComposeTextCreator() }
    viewModel {
        SensorsDataViewModel(
            sensorsRepository = get(),
            textCreator = get(),
            startAccelerometerAnalysis = get(),
            stopAccelerometerAnalysis = get(),
            cameraManager = get(),
            ipProvider = get(),
            versionTextProvider = get(),
            systemStateMonitor = get(),
            startGPSCollection = get(),
            startGyroscopeCollection = get(),
            startMagneticFieldCollection = get(),
        )
    }
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
}
