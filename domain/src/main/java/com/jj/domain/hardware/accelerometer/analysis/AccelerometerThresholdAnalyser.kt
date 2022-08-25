package com.jj.domain.hardware.accelerometer.analysis

import com.jj.domain.hardware.model.SensorData
import com.jj.domain.model.analysis.AnalysedSample

interface AccelerometerThresholdAnalyser {
    fun performAnalysis(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample
}