package com.jj.sensorcollector.data.initializers

import android.util.Log
import com.jj.core.data.sensors.GlobalSensorCollector
import com.jj.core.domain.csv.CSVFileCreator
import com.jj.core.domain.events.GlobalEventsRepository
import com.jj.core.domain.sensors.SamplesRepository
import com.jj.core.data.sensors.accelerometer.AccelerometerSampleAnalyser
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.domain.initializers.AppInitializer
import com.jj.domain.sensors.general.AnalysisStarter
import com.jj.core.domain.monitors.SystemStateMonitor
import com.jj.core.domain.gps.GPSPathAnalyser
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DefaultAppInitializer(
    private val globalSensorCollector: GlobalSensorCollector,
    private val samplesRepository: SamplesRepository,
    private val globalEventsRepository: GlobalEventsRepository,
    private val csvFileCreator: CSVFileCreator,
    private val accelerometerSampleAnalyser: AccelerometerSampleAnalyser,
    private val analysisStarter: AnalysisStarter,
    private val serverStarter: com.jj.server.domain.server.ServerStarter,
    private val systemStateMonitor: SystemStateMonitor,
    private val gpsPathAnalyser: GPSPathAnalyser,
    private val coroutineScopeProvider: CoroutineScopeProvider
) : AppInitializer {

    override fun initialize() {
        globalSensorCollector.start()
        analysisStarter.startPermanentAccelerometerAnalysis()
        analysisStarter.startPermanentGPSAnalysis()
        serverStarter.startServer(8080)
        systemStateMonitor.startMonitoring()
        gpsPathAnalyser.start()
        accelerometerSampleAnalyser.startAnalysis()
//        CollectingDataService.startCollectingGPS(this)
//        CollectingDataService.startCollectingAccelerometer(this)

        coroutineScopeProvider.getIOScope().launch {
            samplesRepository.getAccelerationSamples().collect {
                Log.d("ABAB", "Acceleration samples size: ${it.size}")
            }
        }

        coroutineScopeProvider.getIOScope().launch {
            samplesRepository.getGPSSamples().collect {
                Log.d("ABAB", "GPS samples size: ${it.size}, $it")
            }
        }

        coroutineScopeProvider.getIOScope().launch {
            globalEventsRepository.getGlobalEvents().collect {
                Log.d("ABABX", "Events size: ${it.size}, $it")
            }
        }

        coroutineScopeProvider.getIOScope().launch {
            csvFileCreator.createCSVFile(samplesRepository.getAccelerationSamples().first().map {
                listOf(
                    it.x.toString().replace(".", ","),
                    it.y.toString().replace(".", ","),
                    it.z.toString().replace(".", ","),
                    it.time.toString()
                )
            }, fileName = "/AccelerationSamples.csv")
        }

        coroutineScopeProvider.getIOScope().launch {
            csvFileCreator.createCSVFile(samplesRepository.getGPSSamples().first().map {
                listOf(
                    it.lat.toString().replace(".", ","),
                    it.lng.toString().replace(".", ","),
                    it.time.toString()
                )
            }, fileName = "/GPSSamples.csv")
        }

        coroutineScopeProvider.getIOScope().launch {
            csvFileCreator.createCSVFile(globalEventsRepository.getGlobalEvents().first().map {
                listOf(
                    it.eventType,
                    it.eventTime.toString()
                )
            }, fileName = "/Events.csv")
        }
    }
}