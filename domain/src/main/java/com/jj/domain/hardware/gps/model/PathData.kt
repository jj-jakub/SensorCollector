package com.jj.domain.hardware.gps.model

import com.jj.domain.model.analysis.AnalysedSample
import java.util.Date

data class PathData(
    val id: Int,
    val startTime: Date,
    val endTime: Date,
    val pathNodes: List<AnalysedSample.AnalysedGPSSample>
)