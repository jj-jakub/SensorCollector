package com.jj.core.framework.data.analysis

import com.jj.domain.model.analysis.AnalysedSampleUIData
import com.jj.domain.model.analysis.AnalysedSample

data class AndroidAnalysedAccUIData<StringType>(
    override val analysedSample: AnalysedSample.AnalysedAccSample,
    override val analysedSampleString: StringType
) : AnalysedSampleUIData<StringType>