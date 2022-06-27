package com.jj.sensorcollector.playground1.framework.domain.managers

import android.content.Context
import com.jj.sensorcollector.playground1.framework.service.SensorCollectorService
import com.jj.sensors.domain.managers.AnalyzerStarter

class AndroidAnalyzerStarter(
    private val context: Context
) : AnalyzerStarter {

    override fun startPermanentAccelerometerAnalysis() {
        SensorCollectorService.startCollectingAccelerometer(context)
    }

    override fun stopPermanentAccelerometerAnalysis() {
        SensorCollectorService.stopCollectingAccelerometer(context)
    }

    override fun startPermanentGPSAnalysis() {
        SensorCollectorService.startCollectingGPS(context)
    }
}