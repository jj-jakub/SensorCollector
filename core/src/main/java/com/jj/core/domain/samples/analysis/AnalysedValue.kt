package com.jj.core.domain.samples.analysis

data class AnalysedValue<Value>(
    val value: Value,
    val analysisResult: AnalysisResult
)