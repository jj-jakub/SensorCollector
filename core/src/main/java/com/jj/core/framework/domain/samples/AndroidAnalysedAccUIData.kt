package com.jj.core.framework.domain.samples

import com.jj.core.domain.samples.analysis.AnalysedSample

data class AndroidAnalysedAccUIData<StringType>(
    override val analysedSample: AnalysedSample.AnalysedAccSample,
    override val analysedSampleString: StringType
) : AndroidAnalysedSampleUIData<StringType>