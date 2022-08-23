package com.jj.domain.model.analysis.analysis

data class AnalysedValue<Value>(
    val value: Value,
    val analysisResult: AnalysisResult
)