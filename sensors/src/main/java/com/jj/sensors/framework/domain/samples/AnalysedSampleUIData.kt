package com.jj.sensors.framework.domain.samples

import com.jj.sensors.domain.samples.analysis.AnalysedSample

interface AnalysedSampleUIData<FrameworkColorText> {

    val analysedSample: AnalysedSample
    val analysedSampleString: FrameworkColorText
}