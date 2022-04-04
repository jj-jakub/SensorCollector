package com.jj.sensorcollector.playground1.domain.ui

import com.jj.sensorcollector.playground1.domain.samples.AnalysisResult

interface ColorMapper<FrameworkOutputColor> {

    fun AnalysisResult.toDomainColor(): DomainColor
    fun DomainColor.toTextColor(): FrameworkOutputColor
}