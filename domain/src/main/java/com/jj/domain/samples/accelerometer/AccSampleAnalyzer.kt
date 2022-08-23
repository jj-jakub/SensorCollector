package com.jj.domain.samples.accelerometer

import com.jj.domain.sensors.model.SensorData
import com.jj.domain.model.analysis.analysis.AnalysedSample

interface AccSampleAnalyzer {

    fun performAnalysis(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample
}