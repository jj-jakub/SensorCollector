package com.jj.domain.samples.accelerometer

import com.jj.domain.model.sensors.SensorData
import com.jj.domain.model.analysis.analysis.AnalysedSample

interface AccSampleAnalyzer {

    fun analyze(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample
}