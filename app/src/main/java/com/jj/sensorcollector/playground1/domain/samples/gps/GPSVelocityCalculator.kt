package com.jj.sensorcollector.playground1.domain.samples.gps

import com.jj.sensors.domain.samples.analysis.AnalysedSample

interface GPSVelocityCalculator {

    fun calculateAverageVelocity(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double
}