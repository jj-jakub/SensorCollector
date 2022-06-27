package com.jj.core.domain.samples.accelerometer

import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.samples.analysis.AnalysedSample

interface AccSampleAnalyzer {

    fun analyze(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample
}