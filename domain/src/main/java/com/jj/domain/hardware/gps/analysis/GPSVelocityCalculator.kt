package com.jj.domain.hardware.gps.analysis

import com.jj.domain.model.analysis.AnalysedSample

interface GPSVelocityCalculator {
    fun calculateAverageVelocity(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double
}