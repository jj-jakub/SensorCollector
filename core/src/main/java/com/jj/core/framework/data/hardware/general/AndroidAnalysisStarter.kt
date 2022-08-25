package com.jj.core.framework.data.hardware.general

import android.content.Context
import com.jj.core.framework.service.SensorCollectorService
import com.jj.domain.hardware.general.AnalysisStarter

class AndroidAnalysisStarter(
    private val context: Context
) : AnalysisStarter {

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