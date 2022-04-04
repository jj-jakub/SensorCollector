package com.jj.sensorcollector.playground1.domain.samples

import com.jj.sensorcollector.playground1.domain.SensorData

sealed class AnalysedSample {
    data class AnalysedAccSample(
        val analysedX: AnalysedValue<Float?>,
        val analysedY: AnalysedValue<Float?>,
        val analysedZ: AnalysedValue<Float?>
    ) : AnalysedSample()

    data class AnalysedGyroscopeSample(
        val analysedX: AnalysedValue<Float?>,
        val analysedY: AnalysedValue<Float?>,
        val analysedZ: AnalysedValue<Float?>
    ) : AnalysedSample()

    data class AnalysedMagneticFieldSample(
        val analysedX: AnalysedValue<Float?>,
        val analysedY: AnalysedValue<Float?>,
        val analysedZ: AnalysedValue<Float?>
    ) : AnalysedSample()

    data class Error(
        val sensorData: SensorData,
        val errorCause: String
    ) : AnalysedSample()
}