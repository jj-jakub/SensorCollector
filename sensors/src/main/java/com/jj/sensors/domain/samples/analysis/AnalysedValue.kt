package com.jj.sensors.domain.samples.analysis

data class AnalysedValue<Value>(
    val value: Value,
    val analysisResult: AnalysisResult
)