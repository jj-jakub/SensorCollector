package com.jj.sensorcollector.playground1.framework.data.managers

import android.content.Context
import com.jj.sensorcollector.playground1.domain.managers.AnalyzerStarter
import com.jj.sensorcollector.playground1.framework.service.SensorCollectorService

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