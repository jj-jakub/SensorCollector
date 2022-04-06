package com.jj.sensorcollector.playground1.data.database.accelerometer

import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedValue

fun AnalysedAccelerometerSampleEntity.toAnalysedAccSample(): AnalysedSample.AnalysedAccSample = AnalysedSample.AnalysedAccSample(
    analysedX = AnalysedValue(analysedXValue, analysedXResult),
    analysedY = AnalysedValue(analysedYValue, analysedYResult),
    analysedZ = AnalysedValue(analysedZValue, analysedZResult),
    sampleTime = this.sampleTime
)

fun AnalysedSample.AnalysedAccSample.toAccelerationDataEntity() = AnalysedAccelerometerSampleEntity(
    analysedXValue = this.analysedX.value,
    analysedXResult = this.analysedX.analysisResult,
    analysedYValue = this.analysedY.value,
    analysedYResult = this.analysedY.analysisResult,
    analysedZValue = this.analysedZ.value,
    analysedZResult = this.analysedZ.analysisResult,
    sampleTime = this.sampleTime
)