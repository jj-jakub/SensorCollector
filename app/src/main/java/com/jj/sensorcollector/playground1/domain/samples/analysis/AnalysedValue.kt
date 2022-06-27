package com.jj.sensorcollector.playground1.domain.samples.analysis

import com.jj.sensors.domain.samples.analysis.AnalysisResult

data class AnalysedValue<Value>(
    val value: Value,
    val analysisResult: AnalysisResult
)