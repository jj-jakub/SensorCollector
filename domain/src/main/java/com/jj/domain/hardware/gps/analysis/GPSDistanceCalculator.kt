package com.jj.domain.hardware.gps.analysis

import com.jj.domain.model.analysis.AnalysedSample

interface GPSDistanceCalculator {
    fun calculateCurrentDistance(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double
    fun calculateStackedDistance(
        currentDistance: Double,
        lastSample: AnalysedSample.AnalysedGPSSample,
        nextSample: AnalysedSample.AnalysedGPSSample
    ): Double

    fun calculateAllSamplesDistance(samples: List<AnalysedSample.AnalysedGPSSample>): Double
}