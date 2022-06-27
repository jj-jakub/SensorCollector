package com.jj.sensors.domain.samples.analysis

sealed class AnalysisResult {
    object Normal: AnalysisResult()
    object AboveThreshold: AnalysisResult()
    object Critical: AnalysisResult()
    object Unknown: AnalysisResult()
}
