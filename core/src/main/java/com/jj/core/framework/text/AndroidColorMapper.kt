package com.jj.core.framework.text

import android.graphics.Color
import com.jj.domain.model.analysis.analysis.AnalysisResult
import com.jj.core.domain.ui.colors.ColorMapper
import com.jj.core.domain.ui.colors.DomainColor

object AndroidColorMapper : ColorMapper<Int> {

    // TODO This should be related to styles.
    override fun DomainColor.toTextColor(): Int =
        when (this) {
            DomainColor.Green -> Color.GREEN
            DomainColor.Red -> Color.RED
            DomainColor.Yellow -> Color.YELLOW
            DomainColor.Orange -> Color.rgb(255, 165, 0)
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