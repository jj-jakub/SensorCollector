package com.jj.domain.model.analysis

data class AnalysedValue<Value>(
    val value: Value,
    val analysisResult: AnalysisResult
)