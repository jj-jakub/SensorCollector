package com.jj.sensorcollector.playground1.domain.samples

import com.jj.sensorcollector.playground1.domain.SensorData

interface AccSampleAnalyzer {

    fun analyze(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample
}