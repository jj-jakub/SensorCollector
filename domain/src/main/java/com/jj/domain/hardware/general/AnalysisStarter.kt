package com.jj.domain.hardware.general

interface AnalysisStarter {

    fun startPermanentAccelerometerAnalysis()
    fun stopPermanentAccelerometerAnalysis()

    fun startPermanentGPSAnalysis()
}