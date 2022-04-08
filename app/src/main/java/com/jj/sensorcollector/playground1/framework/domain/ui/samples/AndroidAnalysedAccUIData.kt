package com.jj.sensorcollector.playground1.framework.domain.ui.samples

import android.text.Spannable
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.framework.domain.ui.samples.AndroidAnalysedSampleUIData

data class AndroidAnalysedAccUIData(
    override val analysedSample: AnalysedSample.AnalysedAccSample,
    override val analysedSampleString: Spannable
) : AndroidAnalysedSampleUIData