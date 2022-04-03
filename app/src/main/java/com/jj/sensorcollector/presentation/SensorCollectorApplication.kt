package com.jj.sensorcollector.presentation

import android.app.Application
import android.util.Log
import com.jj.sensorcollector.data.sensors.GlobalSensorCollector
import com.jj.sensorcollector.di.koin.KoinLauncher
import com.jj.sensorcollector.domain.csv.CSVFileCreator
import com.jj.sensorcollector.domain.events.GlobalEventsRepository
import com.jj.sensorcollector.domain.sensors.SamplesRepository
import com.jj.sensorcollector.playground1.data.Initializator
import com.jj.sensorcollector.playground1.data.SampleAnalyzer
import com.jj.sensorcollector.playground1.domain.AnalyzerStarter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SensorCollectorApplication : Application() {

    private val koinLauncher = KoinLauncher()
    private val globalSensorCollector: GlobalSensorCollector by inject()
    private val samplesRepository: SamplesRepository by inject()
    private val globalEventsRepository: GlobalEventsRepository by inject()
    private val csvFileCreator: CSVFileCreator by inject()
    private val sampleAnalyzer: SampleAnalyzer by inject()
    private val initializator: Initializator by inject()
    private val analyzerStarter: AnalyzerStarter by inject()

    override fun onCreate() {
        super.onCreate()
        koinLauncher.startKoin(this)
//        globalSensorCollector.ping()
        analyzerStarter.startPermanentAccelerometerAnalysis()
//        sampleAnalyzer.startAnalysis()
//        initializator
//        CollectingDataService.startCollectingGPS(this)
//        CollectingDataService.startCollectingAccelerometer(this)

        CoroutineScope(Dispatchers.IO).launch {
            samplesRepository.getAccelerationSamples().collect {
                Log.d("ABAB", "Acceleration samples size: ${it.size}")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            samplesRepository.getGPSSamples().collect {
                Log.d("ABAB", "GPS samples size: ${it.size}, $it")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            globalEventsRepository.getGlobalEvents().collect {
                Log.d("ABABX", "Events size: ${it.size}, $it")
            }
        }

        CoroutineScope(Dispatchers.IO).launch {
            csvFileCreator.createCSVFile(samplesRepository.getAccelerationSamples().first().map {
                listOf(
                    it.x.toString().replace(".", ","),
                    it.y.toString().replace(".", ","),
                    it.z.toString().replace(".", ","),
                    it.time.toString()
                )
            }, fileName = "/AccelerationSamples.csv")
        }

        CoroutineScope(Dispatchers.IO).launch {
            csvFileCreator.createCSVFile(samplesRepository.getGPSSamples().first().map {
                listOf(
                    it.lat.toString().replace(".", ","),
                    it.lng.toString().replace(".", ","),
                    it.time.toString()
                )
            }, fileName = "/GPSSamples.csv")
        }

        CoroutineScope(Dispatchers.IO).launch {
            csvFileCreator.createCSVFile(globalEventsRepository.getGlobalEvents().first().map {
                listOf(
                    it.eventType,
                    it.eventTime.toString()
                )
            }, fileName = "/Events.csv")
        }
    }
}