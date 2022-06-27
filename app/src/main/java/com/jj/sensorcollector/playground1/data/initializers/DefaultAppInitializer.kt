package com.jj.sensorcollector.playground1.data.initializers
import android.util.Log
import com.jj.sensorcollector.data.sensors.GlobalSensorCollector
import com.jj.sensorcollector.domain.csv.CSVFileCreator
import com.jj.sensorcollector.domain.events.GlobalEventsRepository
import com.jj.sensorcollector.domain.sensors.SamplesRepository
import com.jj.sensorcollector.playground1.data.AccelerometerSampleAnalyzer
import com.jj.core.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.playground1.domain.initializers.AppInitializer
import com.jj.sensors.domain.managers.AnalyzerStarter
import com.jj.sensorcollector.playground1.domain.monitors.SystemStateMonitor
import com.jj.sensors.domain.samples.gps.GPSPathAnalyser
import com.jj.sensorcollector.playground1.domain.server.ServerStarter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DefaultAppInitializer(
    private val globalSensorCollector: GlobalSensorCollector,
    private val samplesRepository: SamplesRepository,
    private val globalEventsRepository: GlobalEventsRepository,
    private val csvFileCreator: CSVFileCreator,
    private val accelerometerSampleAnalyzer: AccelerometerSampleAnalyzer,
    private val analyzerStarter: AnalyzerStarter,
    private val serverStarter: ServerStarter,
    private val systemStateMonitor: SystemStateMonitor,
    private val gpsPathAnalyser: GPSPathAnalyser,
    private val coroutineScopeProvider: com.jj.core.coroutines.CoroutineScopeProvider
) : AppInitializer {

    override fun initialize() {
//        globalSensorCollector.ping()
        analyzerStarter.startPermanentAccelerometerAnalysis()
        analyzerStarter.startPermanentGPSAnalysis()
        serverStarter.startServer(8080)
        systemStateMonitor.startMonitoring()
//        sampleAnalyzer.startAnalysis()
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