package com.jj.core.data.database

import androidx.room.TypeConverter
import com.jj.domain.model.analysis.AnalysisResult

class AnalysisResultTypeConverter {
    @TypeConverter
    fun analysisResultToString(analysisResult: AnalysisResult): String =
        when (analysisResult) {
            AnalysisResult.AboveThreshold -> "AboveThreshold"
            AnalysisResult.Critical -> "Critical"
            AnalysisResult.Normal -> "Normal"
            AnalysisResult.Unknown -> "Unknown"
        }

    @TypeConverter
    fun analysisResultFromString(analysisResultString: String): AnalysisResult = when (analysisResultString) {
        "AboveThreshold" -> AnalysisResult.AboveThreshold
        "Critical" -> AnalysisResult.Critical
        "Normal" -> AnalysisResult.Normal
        else -> AnalysisResult.Unknown
    }
}