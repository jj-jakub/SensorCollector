package com.jj.sensorcollector.playground1.framework.domain.ui.samples

import androidx.compose.ui.text.AnnotatedString
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample

data class AndroidAnalysedAccUIData(
    override val analysedSample: AnalysedSample.AnalysedAccSample,
    override val analysedSampleString: AnnotatedString
) : AndroidAnalysedSampleUIData