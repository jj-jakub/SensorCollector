package com.jj.sensorcollector.playground1.domain.samples.gps

import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import java.util.Date

data class PathData(
    val id: Int,
    val startTime: Date,
    val endTime: Date,
    val pathNodes: List<AnalysedSample.AnalysedGPSSample>
)