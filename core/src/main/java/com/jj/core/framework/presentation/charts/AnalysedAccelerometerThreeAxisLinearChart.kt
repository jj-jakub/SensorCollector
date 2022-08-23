package com.jj.core.framework.presentation.charts

import android.content.Context
import android.util.AttributeSet
import com.jj.domain.model.analysis.analysis.AnalysedSample

class AnalysedAccelerometerThreeAxisLinearChart @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseThreeAxisLinearChart(context, attrs) {

    suspend fun updateAccelerometerChart(analysedSample: AnalysedSample.AnalysedAccSample) {
        updateChart(analysedSample.analysedX.value, analysedSample.analysedY.value, analysedSample.analysedZ.value)
    }
}