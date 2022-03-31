package com.jj.sensorcollector.playground1.domain

import com.jj.sensorcollector.playground1.framework.SensorData

interface AccSampleAnalyzer {

    fun analyze(sensorData: SensorData.AccSample): Int
}