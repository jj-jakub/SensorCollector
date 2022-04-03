package com.jj.sensorcollector.playground1.domain


interface AccSampleAnalyzer {

    fun analyze(sensorData: SensorData.AccSample): Int
}