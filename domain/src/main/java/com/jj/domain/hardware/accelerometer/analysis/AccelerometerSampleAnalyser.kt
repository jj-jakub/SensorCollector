package com.jj.domain.hardware.accelerometer.analysis

import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.general.analysis.BaseSampleAnalyser
import com.jj.domain.time.TimeProvider

abstract class AccelerometerSampleAnalyser(
    timeProvider: TimeProvider,
    coroutineScopeProvider: CoroutineScopeProvider,
) : BaseSampleAnalyser(
    timeProvider = timeProvider,
    coroutineScopeProvider = coroutineScopeProvider
)