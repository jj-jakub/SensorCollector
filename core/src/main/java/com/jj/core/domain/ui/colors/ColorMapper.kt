package com.jj.core.domain.ui.colors

import com.jj.core.domain.samples.analysis.AnalysisResult

interface ColorMapper<FrameworkOutputColor> {

    fun AnalysisResult.toDomainColor(): DomainColor
    fun DomainColor.toTextColor(): FrameworkOutputColor
}