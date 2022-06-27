package com.jj.core.data.samples.accelerometer

import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.samples.accelerometer.AccThresholdAnalyzer
import com.jj.core.domain.samples.analysis.AnalysedSample
import com.jj.core.domain.samples.analysis.AnalysedValue
import com.jj.core.domain.samples.analysis.AnalysisResult
import com.jj.core.domain.samples.analysis.ThresholdValues.ACCELEROMETER_AXIS_NORMAL_THRESHOLD_NORMAL_UNTIL
import com.jj.core.domain.samples.analysis.ThresholdValues.ACCELEROMETER_AXIS_THRESHOLD_ABOVE_UNTIL
import com.jj.core.domain.time.TimeProvider
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