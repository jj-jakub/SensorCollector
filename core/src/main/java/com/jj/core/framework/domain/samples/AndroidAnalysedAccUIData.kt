package com.jj.core.framework.domain.samples

import com.jj.domain.model.analysis.analysis.AnalysedSample

data class AndroidAnalysedAccUIData<StringType>(
    override val analysedSample: AnalysedSample.AnalysedAccSample,
    override val analysedSampleString: StringType
) : AndroidAnalysedSampleUIData<StringType>