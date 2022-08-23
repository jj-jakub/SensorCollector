package com.jj.domain.sensors.general

interface AnalysisStarter {

    fun startPermanentAccelerometerAnalysis()
    fun stopPermanentAccelerometerAnalysis()

    fun startPermanentGPSAnalysis()
}