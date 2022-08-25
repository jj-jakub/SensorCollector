package com.jj.core.data.hardware.accelerometer.analysis

import com.jj.domain.hardware.model.SensorData
import com.jj.domain.hardware.accelerometer.analysis.AccelerometerThresholdAnalyser
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.model.analysis.AnalysedValue
import com.jj.domain.model.analysis.AnalysisResult
import com.jj.domain.model.analysis.ThresholdValues.ACCELEROMETER_AXIS_NORMAL_THRESHOLD_NORMAL_UNTIL
import com.jj.domain.model.analysis.ThresholdValues.ACCELEROMETER_AXIS_THRESHOLD_ABOVE_UNTIL
import com.jj.domain.time.TimeProvider
import kotlin.math.abs

class DefaultAccelerometerThresholdAnalyser(
    private val timeProvider: TimeProvider
) : AccelerometerThresholdAnalyser {

    override fun performAnalysis(sensorData: SensorData.AccSample): AnalysedSample.AnalysedAccSample {
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