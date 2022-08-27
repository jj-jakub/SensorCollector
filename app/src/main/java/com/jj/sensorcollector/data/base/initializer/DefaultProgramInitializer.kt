package com.jj.sensorcollector.data.base.initializer

import com.jj.domain.base.initializer.ProgramInitializer
import com.jj.domain.base.usecase.invoke
import com.jj.domain.hardware.general.usecase.StartAnalysis
import com.jj.domain.hardware.gps.analysis.path.GPSPathAnalyser
import com.jj.domain.monitoring.SystemStateMonitor
import com.jj.domain.server.ServerStarter

class DefaultProgramInitializer(
    private val startAnalysis: StartAnalysis,
    private val serverStarter: ServerStarter,
    private val systemStateMonitor: SystemStateMonitor,
    private val gpsPathAnalyser: GPSPathAnalyser, // TODO To use case
) : ProgramInitializer {

    override suspend fun initialize() {
        startAnalysis()
        systemStateMonitor.startMonitoring()
        serverStarter.startServer(8080)
        gpsPathAnalyser.start()
    }
}
