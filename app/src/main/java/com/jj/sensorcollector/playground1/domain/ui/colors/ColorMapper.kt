package com.jj.sensorcollector.playground1.domain.ui.colors

import com.jj.sensors.domain.samples.analysis.AnalysisResult

interface ColorMapper<FrameworkOutputColor> {

    fun AnalysisResult.toDomainColor(): DomainColor
    fun DomainColor.toTextColor(): FrameworkOutputColor
}