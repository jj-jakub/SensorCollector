package com.jj.sensorcollector.playground1.domain.ui.samples

import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample

interface AnalysedSampleUIData<FrameworkColorText> {

    val analysedSample: AnalysedSample
    val analysedSampleString: FrameworkColorText
}