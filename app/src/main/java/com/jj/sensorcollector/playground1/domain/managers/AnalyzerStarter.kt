package com.jj.sensorcollector.playground1.domain.managers

interface AnalyzerStarter {

    fun startPermanentAccelerometerAnalysis()
    fun stopPermanentAccelerometerAnalysis()

    fun startPermanentGPSAnalysis()
}