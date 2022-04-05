package com.jj.sensorcollector.playground1.data.accanalyzers

import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.samples.AccThresholdAnalyzer
import com.jj.sensorcollector.playground1.domain.samples.AnalysedSample
import com.jj.sensorcollector.playground1.domain.samples.AnalysedValue
import com.jj.sensorcollector.playground1.domain.samples.AnalysisResult
import com.jj.sensorcollector.playground1.domain.samples.ThresholdValues.ACCELEROMETER_AXIS_NORMAL_THRESHOLD_NORMAL_UNTIL
import com.jj.sensorcollector.playground1.domain.samples.ThresholdValues.ACCELEROMETER_AXIS_THRESHOLD_ABOVE_UNTIL
import com.jj.sensorcollector.playground1.domain.time.TimeProvider
import kotlin.math.abs

class AccelerometerThresholdAnalyzer(
    private val timeProvider: TimeProvider
) : AccThresholdAnalyzer {

    override fun analyze(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample {
        val analysedX = analyzeFloat(sensorData.x)
        val analysedY = analyzeFloat(sensorData.y)
        val analysedZ = analyzeFloat(sensorData.z)

        return AnalysedSample.AnalysedAccSample(analysedX, analysedY, analysedZ, timeProvider.getNowMillis())
    }

    private fun analyzeFloat(floatValue: Float?): AnalysedValue<Float?> {
        if (floatValue == null) return AnalysedValue(floatValue, AnalysisResult.Unknown)

        // TODO Better double check this conversion or create own abs() for floats
        val absValue = abs(floatValue.toDouble())

        val analysisResult = when {
            absValue < ACCELEROMETER_AXIS_NORMAL_THRESHOLD_NORMAL_UNTIL -> {
                AnalysisResult.Normal
            }
            absValue >= ACCELEROMETER_AXIS_NORMAL_THRESHOLD_NORMAL_UNTIL && absValue < ACCELEROMETER_AXIS_THRESHOLD_ABOVE_UNTIL -> {
                AnalysisResult.AboveThreshold
            }
            absValue > ACCELEROMETER_AXIS_THRESHOLD_ABOVE_UNTIL -> {
                AnalysisResult.Critical
            }
            else -> {
                AnalysisResult.Unknown
            }
        }

        return AnalysedValue(floatValue, analysisResult)
    }
}