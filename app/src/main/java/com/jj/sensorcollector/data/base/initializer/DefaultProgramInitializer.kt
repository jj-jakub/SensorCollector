package com.jj.sensorcollector.data.base.initializer

import com.jj.core.data.hardware.accelerometer.analysis.DefaultAccelerometerSampleAnalyser
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.csv.CSVFileCreator
import com.jj.domain.hardware.gps.analysis.GPSPathAnalyser
import com.jj.domain.monitoring.SystemStateMonitor
import com.jj.domain.hardware.general.AnalysisStarter
import com.jj.domain.server.ServerStarter
import com.jj.domain.base.initializer.ProgramInitializer

class DefaultProgramInitializer(
    private val csvFileCreator: CSVFileCreator,
    private val defaultAccelerometerSampleAnalyser: DefaultAccelerometerSampleAnalyser,
    private val analysisStarter: AnalysisStarter,
    private val serverStarter: ServerStarter,
    private val systemStateMonitor: SystemStateMonitor,
    private val gpsPathAnalyser: GPSPathAnalyser,
    private val coroutineScopeProvider: CoroutineScopeProvider
) : ProgramInitializer {

    override fun initialize() {
        analysisStarter.startPermanentAccelerometerAnalysis()
        analysisStarter.startPermanentGPSAnalysis()
        serverStarter.startServer(8080)
        systemStateMonitor.startMonitoring()
        gpsPathAnalyser.start()
        defaultAccelerometerSampleAnalyser.startAnalysis()

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
