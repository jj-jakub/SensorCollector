package com.jj.sensorcollector.playground1.framework.domain.ui.samples

import androidx.compose.ui.text.AnnotatedString
import com.jj.sensors.domain.samples.analysis.AnalysedSample
import com.jj.sensors.framework.domain.ui.samples.AndroidAnalysedSampleUIData

data class AndroidAnalysedAccUIData(
    override val analysedSample: AnalysedSample.AnalysedAccSample,
    override val analysedSampleString: AnnotatedString
) : AndroidAnalysedSampleUIData