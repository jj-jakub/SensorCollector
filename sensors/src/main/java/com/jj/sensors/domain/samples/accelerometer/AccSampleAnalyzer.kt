package com.jj.sensors.domain.samples.accelerometer

import com.jj.sensors.domain.samples.SensorData
import com.jj.sensors.domain.samples.analysis.AnalysedSample

interface AccSampleAnalyzer {

    fun analyze(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample
}