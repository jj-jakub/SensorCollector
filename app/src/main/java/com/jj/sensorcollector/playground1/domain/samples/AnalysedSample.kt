package com.jj.sensorcollector.playground1.domain.samples

import com.jj.sensorcollector.playground1.domain.SensorData

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

    data class Error(
        val sensorData: SensorData,
        val errorCause: String,
        val errorTime: Long
    ) : AnalysedSample(errorTime)
}