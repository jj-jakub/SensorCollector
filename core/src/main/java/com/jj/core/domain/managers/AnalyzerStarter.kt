package com.jj.core.domain.managers

interface AnalyzerStarter {

    fun startPermanentAccelerometerAnalysis()
    fun stopPermanentAccelerometerAnalysis()

    fun startPermanentGPSAnalysis()
}