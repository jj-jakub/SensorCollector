package com.jj.core.data.samples.accelerometer

import com.jj.domain.sensors.model.SensorData
import com.jj.domain.samples.accelerometer.AccThresholdAnalyser
import com.jj.domain.model.analysis.analysis.AnalysedSample
import com.jj.domain.model.analysis.analysis.AnalysedValue
import com.jj.domain.model.analysis.analysis.AnalysisResult
import com.jj.domain.model.analysis.analysis.ThresholdValues.ACCELEROMETER_AXIS_NORMAL_THRESHOLD_NORMAL_UNTIL
import com.jj.domain.model.analysis.analysis.ThresholdValues.ACCELEROMETER_AXIS_THRESHOLD_ABOVE_UNTIL
import com.jj.core.domain.time.TimeProvider
import kotlin.math.abs

class AccelerometerThresholdAnalyser(
    private val timeProvider: TimeProvider
) : AccThresholdAnalyser {

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