package com.jj.sensors.domain.ui.samples

import com.jj.domain.model.analysis.analysis.AnalysedSample


interface AnalysedSampleUIData<FrameworkColorText> {

    val analysedSample: AnalysedSample
    val analysedSampleString: FrameworkColorText
}