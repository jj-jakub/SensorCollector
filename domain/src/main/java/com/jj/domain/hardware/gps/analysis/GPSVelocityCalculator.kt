package com.jj.domain.hardware.gps.analysis

import com.jj.domain.model.analysis.AnalysedSample

interface GPSVelocityCalculator {
    fun calculateCurrentVelocity(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double
    fun calculateStackedAverageVelocity(
        currentAverageVelocity: Double,
        currentSamplesAmount: Int,
        lastSample: AnalysedSample.AnalysedGPSSample,
        nextSample: AnalysedSample.AnalysedGPSSample
    ): Double

    fun calculateAllSamplesAverageVelocity(samples: List<AnalysedSample.AnalysedGPSSample>): Double
}