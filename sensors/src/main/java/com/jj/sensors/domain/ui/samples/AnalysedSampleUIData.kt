package com.jj.sensors.domain.ui.samples

import com.jj.core.domain.samples.analysis.AnalysedSample

interface AnalysedSampleUIData<FrameworkColorText> {

    val analysedSample: AnalysedSample
    val analysedSampleString: FrameworkColorText
}