package com.jj.sensorcollector.playground1.domain.samples.accelerometer

import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample

interface AccSampleAnalyzer {

    fun analyze(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample
}