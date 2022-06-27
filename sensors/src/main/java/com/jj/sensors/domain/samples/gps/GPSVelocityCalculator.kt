package com.jj.sensors.domain.samples.gps

import com.jj.sensors.domain.samples.analysis.AnalysedSample

interface GPSVelocityCalculator {

    fun calculateAverageVelocity(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double
}