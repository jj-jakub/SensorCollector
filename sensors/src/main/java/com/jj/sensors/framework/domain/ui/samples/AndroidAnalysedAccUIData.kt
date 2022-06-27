package com.jj.sensors.framework.domain.ui.samples

import com.jj.core.domain.samples.analysis.AnalysedSample

data class AndroidAnalysedAccUIData(
    override val analysedSample: AnalysedSample.AnalysedAccSample,
    override val analysedSampleString: String
) : AndroidAnalysedSampleUIData