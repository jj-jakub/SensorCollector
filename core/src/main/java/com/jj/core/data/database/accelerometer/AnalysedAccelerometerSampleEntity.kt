package com.jj.core.data.database.accelerometer

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jj.domain.model.analysis.analysis.AnalysisResult

@Entity
data class AnalysedAccelerometerSampleEntity(
    @PrimaryKey
    val id: Int? = null,
    val analysedXValue: Float?,
    val analysedXResult: AnalysisResult,
    val analysedYValue: Float?,
    val analysedYResult: AnalysisResult,
    val analysedZValue: Float?,
    val analysedZResult: AnalysisResult,
    val sampleTime: Long
)