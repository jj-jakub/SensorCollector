package com.jj.sensorcollector.di.koin

import androidx.compose.ui.text.AnnotatedString
import com.jj.core.data.samples.gps.DefaultGPSPathAnalyser
import com.jj.core.data.samples.gps.DefaultGPSVelocityCalculator
import com.jj.core.data.sensors.AccelerometerDataCollector
import com.jj.core.data.sensors.GPSDataCollector
import com.jj.core.data.sensors.GlobalSensorCollector
import com.jj.core.data.sensors.GlobalSensorManager
import com.jj.core.data.text.VersionTextProvider
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.events.EventsCollector
import com.jj.core.domain.managers.VibrationManager
import com.jj.core.domain.monitors.SystemStateMonitor
import com.jj.core.domain.samples.samples.gps.GPSPathAnalyser
import com.jj.core.domain.samples.samples.gps.GPSVelocityCalculator
import com.jj.core.domain.sensors.IGlobalSensorManager
import com.jj.core.domain.ui.text.TextCreator
import com.jj.core.framework.notification.NotificationManagerBuilder
import com.jj.core.framework.presentation.SensorsDataViewModel
import com.jj.core.framework.text.ComposeTextCreator
import com.jj.sensorcollector.data.GlobalEventsCollector
import com.jj.sensorcollector.data.csv.DefaultCSVFileCreator
import com.jj.sensorcollector.domain.csv.CSVFileCreator
import com.jj.sensorcollector.playground1.data.initializers.DefaultAppInitializer
import com.jj.sensorcollector.playground1.domain.initializers.AppInitializer
import com.jj.sensors.data.monitors.DefaultAccelerometerStateMonitor
import com.jj.sensors.data.monitors.DefaultGPSStateMonitor
import com.jj.sensors.data.monitors.DefaultGyroscopeStateMonitor
import com.jj.sensors.data.monitors.DefaultMagneticFieldStateMonitor
import com.jj.sensors.data.monitors.DefaultSystemStateMonitor
import com.jj.sensors.domain.monitors.markers.AccelerometerStateMonitor
import com.jj.sensors.domain.monitors.markers.GPSStateMonitor
import com.jj.sensors.domain.monitors.markers.GyroscopeStateMonitor
import com.jj.sensors.domain.monitors.markers.MagneticFieldStateMonitor
import com.jj.server.data.server.network.RetrofitFactory
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

    single { RetrofitFactory() }
    single { VersionTextProvider() }

    single { AccelerometerDataCollector() }
    single { GPSDataCollector() }

    single { GlobalSensorCollector(get(), get(), get(), get()) }
    single { NotificationManagerBuilder() }

    single<CoroutineScopeProvider> { com.jj.core.data.coroutines.DefaultCoroutineScopeProvider() }

    single<EventsCollector> { GlobalEventsCollector(get(), get()) }
    single<CSVFileCreator> { DefaultCSVFileCreator(androidContext()) }

    single<IGlobalSensorManager> { GlobalSensorManager(get(), get(), get()) }


//    single<TextCreator<Spannable>> { AndroidTextCreator() }
    single<TextCreator<AnnotatedString>> { ComposeTextCreator() }
    viewModel { SensorsDataViewModel(get(), get(), get(), get(), get(), get()) }

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