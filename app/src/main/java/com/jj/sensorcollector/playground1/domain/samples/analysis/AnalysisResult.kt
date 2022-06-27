package com.jj.sensorcollector.playground1.domain.samples.analysis

import com.jj.sensors.domain.samples.analysis.AnalysisResult

sealed class AnalysisResult {
    object Normal: AnalysisResult()
    object AboveThreshold: AnalysisResult()
    object Critical: AnalysisResult()
    object Unknown: AnalysisResult()
}
