package com.jj.sensorcollector.playground1.framework.ui.text

import android.graphics.Color
import com.jj.sensorcollector.playground1.domain.samples.AnalysisResult
import com.jj.sensorcollector.playground1.domain.ui.ColorMapper
import com.jj.sensorcollector.playground1.domain.ui.DomainColor

object AndroidColorMapper : ColorMapper<Int> {

    override fun DomainColor.toTextColor(): Int =
        when (this) {
            DomainColor.Green -> Color.GREEN
            DomainColor.Red -> Color.RED
            DomainColor.Yellow -> Color.YELLOW
            DomainColor.Default -> Color.BLACK // TODO Find default color here?x
        }

    override fun AnalysisResult.toDomainColor(): DomainColor =
        when (this) {
            AnalysisResult.AboveThreshold -> DomainColor.Yellow
            AnalysisResult.Critical -> DomainColor.Red
            AnalysisResult.Normal -> DomainColor.Green
            AnalysisResult.Unknown -> DomainColor.Default
        }
}