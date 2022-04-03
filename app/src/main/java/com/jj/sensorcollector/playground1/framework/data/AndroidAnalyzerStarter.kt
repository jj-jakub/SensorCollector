package com.jj.sensorcollector.playground1.framework.data

import android.content.Context
import com.jj.sensorcollector.playground1.domain.AnalyzerStarter
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
}