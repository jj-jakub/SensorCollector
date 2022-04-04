package com.jj.sensorcollector.playground1.domain.samples

sealed class AnalysisResult {
    object Normal: AnalysisResult()
    object AboveThreshold: AnalysisResult()
    object Critical: AnalysisResult()
    object Unknown: AnalysisResult()
}
