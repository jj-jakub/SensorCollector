package com.jj.core.domain.samples

import com.jj.core.domain.samples.analysis.AnalysedSample

interface AnalysedSampleUIData<FrameworkColorText> {

    val analysedSample: AnalysedSample
    val analysedSampleString: FrameworkColorText
}