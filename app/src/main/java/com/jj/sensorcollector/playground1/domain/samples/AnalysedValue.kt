package com.jj.sensorcollector.playground1.domain.samples

data class AnalysedValue<Value>(
    val value: Value,
    val analysisResult: AnalysisResult
)