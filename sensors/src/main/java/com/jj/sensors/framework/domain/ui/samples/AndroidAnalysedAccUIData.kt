package com.jj.sensors.framework.domain.ui.samples

import androidx.compose.ui.text.AnnotatedString
import com.jj.sensors.domain.samples.analysis.AnalysedSample

data class AndroidAnalysedAccUIData(
    override val analysedSample: AnalysedSample.AnalysedAccSample,
    override val analysedSampleString: AnnotatedString
) : AndroidAnalysedSampleUIData