package com.jj.sensorcollector.playground1.domain.samples.analysis

import com.jj.sensors.domain.samples.SensorData
import com.jj.sensors.domain.samples.analysis.AnalysedSample
import com.jj.sensors.domain.samples.analysis.AnalysedValue

sealed class AnalysedSample(val time: Long) {
    data class AnalysedAccSample(
        val analysedX: AnalysedValue<Float?>,
        val analysedY: AnalysedValue<Float?>,
        val analysedZ: AnalysedValue<Float?>,
        val sampleTime: Long
    ) : AnalysedSample(sampleTime)

    data class AnalysedGyroscopeSample(
        val analysedX: AnalysedValue<Float?>,
        val analysedY: AnalysedValue<Float?>,
        val analysedZ: AnalysedValue<Float?>,
        val sampleTime: Long
    ) : AnalysedSample(sampleTime)

    data class AnalysedMagneticFieldSample(
        val analysedX: AnalysedValue<Float?>,
        val analysedY: AnalysedValue<Float?>,
        val analysedZ: AnalysedValue<Float?>,
        val sampleTime: Long
    ) : AnalysedSample(sampleTime)

    data class AnalysedGPSSample(
        val latitude: Double,
        val longitude: Double,
        val sampleTime: Long
    ) : AnalysedSample(sampleTime)

    data class Error(
        val sensorData: SensorData,
        val errorCause: String,
        val errorTime: Long
    ) : AnalysedSample(errorTime)
}