package com.jj.core.di

import androidx.compose.ui.text.AnnotatedString
import androidx.room.Room
import com.jj.core.data.api.DefaultAccelerometerAPI
import com.jj.core.data.csv.DefaultCSVFileCreator
import com.jj.core.data.database.AnalysedSamplesDatabase
import com.jj.core.data.database.SamplesDatabase
import com.jj.core.data.hardware.accelerometer.analysis.DefaultAccelerometerSampleAnalyser
import com.jj.core.data.hardware.accelerometer.analysis.DefaultAccelerometerThresholdAnalyser
import com.jj.core.data.hardware.accelerometer.repository.DefaultAccelerometerRepository
import com.jj.core.data.hardware.general.DefaultSensorsRepository
import com.jj.core.data.hardware.gps.analysis.DefaultGPSPathAnalyser
import com.jj.core.data.hardware.gps.analysis.DefaultGPSSampleAnalyser
import com.jj.core.data.hardware.gps.analysis.HaversineGPSVelocityCalculator
import com.jj.core.data.hardware.gps.analysis.VelocityCalculatorBufferPersistence
import com.jj.core.data.hardware.gps.repository.DefaultGPSRepository
import com.jj.core.data.hardware.gps.repository.DefaultPathRepository
import com.jj.core.data.hardware.gyroscope.DefaultGyroscopeRepository
import com.jj.core.data.hardware.magneticfield.DefaultMagneticFieldRepository
import com.jj.core.data.server.DefaultRemoteControlManager
import com.jj.core.data.text.VersionTextProvider
import com.jj.core.data.time.DefaultTimeProvider
import com.jj.core.data.travel.database.TravelDatabase
import com.jj.core.data.travel.repository.DefaultTravelRepository
import com.jj.core.framework.data.hardware.camera.AndroidCameraManager
import com.jj.core.framework.data.hardware.camera.CameraManager
import com.jj.core.framework.data.hardware.camera.CameraXProvider
import com.jj.core.framework.data.hardware.general.AndroidAnalysisStarter
import com.jj.core.framework.notification.NotificationManagerBuilder
import com.jj.core.framework.presentation.camera.CameraScreenViewModel
import com.jj.core.framework.presentation.sensors.SensorsDataViewModel
import com.jj.core.framework.presentation.settings.SettingsScreenViewModel
import com.jj.core.framework.presentation.travel.TravelScreenViewModel
import com.jj.core.framework.presentation.uiplayground.UIPlaygroundScreenViewModel
import com.jj.core.framework.presentation.velocity.VelocityScreenViewModel
import com.jj.core.framework.text.ComposeTextCreator
import com.jj.domain.api.AccelerometerAPI
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.csv.CSVFileCreator
import com.jj.domain.hardware.accelerometer.analysis.AccelerometerSampleAnalyser
import com.jj.domain.hardware.accelerometer.analysis.AccelerometerThresholdAnalyser
import com.jj.domain.hardware.accelerometer.repository.AccelerometerRepository
import com.jj.domain.hardware.general.AnalysisStarter
import com.jj.domain.hardware.general.SensorsRepository
import com.jj.domain.hardware.gps.analysis.GPSPathAnalyser
import com.jj.domain.hardware.gps.analysis.GPSSampleAnalyser
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.gps.repository.PathRepository
import com.jj.domain.hardware.gyroscope.repository.GyroscopeRepository
import com.jj.domain.hardware.magneticfield.repository.MagneticFieldRepository
import com.jj.domain.server.RemoteControlManager
import com.jj.domain.time.TimeProvider
import com.jj.domain.travel.repository.TravelRepository
import com.jj.domain.ui.text.TextCreator
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coreModule = module {

    single { Room.databaseBuilder(androidContext(), SamplesDatabase::class.java, "samples_database.db").build() }
    single { Room.databaseBuilder(androidContext(), AnalysedSamplesDatabase::class.java, "analysed_samples_database.db").build() }
    single { Room.databaseBuilder(androidContext(), TravelDatabase::class.java, "travel_database.db").build() }


    single { VersionTextProvider() }
    single<TimeProvider> { DefaultTimeProvider() }
    single<AccelerometerThresholdAnalyser> { DefaultAccelerometerThresholdAnalyser(get()) }
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

    single<AccelerometerSampleAnalyser> { DefaultAccelerometerSampleAnalyser(get(), get(), get(), get()) }
    single<GPSSampleAnalyser> { DefaultGPSSampleAnalyser(get(), get(), get()) }
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
            gpsRepository = get(),
            startGyroscopeCollection = get(),
            startMagneticFieldCollection = get(),
        )
    }
    viewModel { SettingsScreenViewModel(cameraManager = get()) }
    viewModel { CameraScreenViewModel(cameraManager = get()) }
    viewModel {
        TravelScreenViewModel(
            getTravelItemsForList = get(),
            saveTravelItem = get(),
            deleteTravelItem = get()
        )
    }
    viewModel { UIPlaygroundScreenViewModel() }
    viewModel {
        VelocityScreenViewModel(
            gpsStateMonitor = get(),
            gpsRepository = get(),
            velocityCalculatorBufferPersistence = get(),
            startGPSAnalysis = get(),
            stopGPSAnalysis = get(),
        )
    }

    single<RemoteControlManager> { DefaultRemoteControlManager(get(), get()) }

    single { CameraXProvider(androidContext()) }
    single<CameraManager> {
        AndroidCameraManager(
            androidContext(),
            get()
        )
    }

    single<GPSPathAnalyser> {
        DefaultGPSPathAnalyser(
            gpsRepository = get(),
            pathRepository = get(),
            gpsVelocityCalculator = get(),
            coroutineScopeProvider = get()
        )
    }

    single<CSVFileCreator> { DefaultCSVFileCreator(androidContext()) }

    single<GPSVelocityCalculator> { HaversineGPSVelocityCalculator() }
    single {
        VelocityCalculatorBufferPersistence(
            gpsVelocityCalculator = get()
        )
    }
}
