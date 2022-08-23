package com.jj.domain.model.analysis.analysis

sealed class AnalysisResult {
    object Normal: AnalysisResult()
    object AboveThreshold: AnalysisResult()
    object Critical: AnalysisResult()
    object Unknown: AnalysisResult()
}
