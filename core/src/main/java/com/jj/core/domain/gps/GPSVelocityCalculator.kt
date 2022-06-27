package com.jj.core.domain.samples.samples.gps

import com.jj.core.domain.samples.analysis.AnalysedSample

interface GPSVelocityCalculator {

    fun calculateAverageVelocity(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double
}