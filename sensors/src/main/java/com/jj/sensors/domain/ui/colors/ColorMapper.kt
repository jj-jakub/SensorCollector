package com.jj.sensors.domain.ui.colors

import com.jj.core.domain.ui.colors.DomainColor
import com.jj.domain.model.analysis.analysis.AnalysisResult

interface ColorMapper<FrameworkOutputColor> {

    fun AnalysisResult.toDomainColor(): DomainColor
    fun DomainColor.toTextColor(): FrameworkOutputColor
}