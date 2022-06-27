package com.jj.sensors.domain.managers

interface AnalyzerStarter {

    fun startPermanentAccelerometerAnalysis()
    fun stopPermanentAccelerometerAnalysis()

    fun startPermanentGPSAnalysis()
}