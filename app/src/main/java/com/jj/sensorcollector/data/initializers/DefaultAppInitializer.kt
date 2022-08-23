package com.jj.sensorcollector.data.initializers

import com.jj.core.data.sensors.accelerometer.AccelerometerSampleAnalyser
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.csv.CSVFileCreator
import com.jj.core.domain.gps.GPSPathAnalyser
import com.jj.core.domain.monitors.SystemStateMonitor
import com.jj.domain.sensors.general.AnalysisStarter
import com.jj.sensorcollector.domain.initializers.AppInitializer

class DefaultAppInitializer(
    private val csvFileCreator: CSVFileCreator,
    private val accelerometerSampleAnalyser: AccelerometerSampleAnalyser,
    private val analysisStarter: AnalysisStarter,
    private val serverStarter: com.jj.server.domain.server.ServerStarter,
    private val systemStateMonitor: SystemStateMonitor,
    private val gpsPathAnalyser: GPSPathAnalyser,
    private val coroutineScopeProvider: CoroutineScopeProvider
) : AppInitializer {

    override fun initialize() {
        analysisStarter.startPermanentAccelerometerAnalysis()
        analysisStarter.startPermanentGPSAnalysis()
        serverStarter.startServer(8080)
        systemStateMonitor.startMonitoring()
        gpsPathAnalyser.start()
        accelerometerSampleAnalyser.startAnalysis()

//        coroutineScopeProvider.getIOScope().launch {
//            csvFileCreator.createCSVFile(samplesRepository.getAccelerationSamples().first().map {
//                listOf(
//                    it.x.toString().replace(".", ","),
//                    it.y.toString().replace(".", ","),
//                    it.z.toString().replace(".", ","),
//                    it.time.toString()
//                )
//            }, fileName = "/AccelerationSamples.csv")
//        }
    }
}
