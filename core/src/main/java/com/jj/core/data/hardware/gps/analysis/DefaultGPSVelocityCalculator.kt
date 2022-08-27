package com.jj.core.data.hardware.gps.analysis

import com.jj.domain.hardware.gps.analysis.GPSDistanceCalculator
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import com.jj.domain.model.analysis.AnalysedSample

class DefaultGPSVelocityCalculator(
    private val gpsDistanceCalculator: GPSDistanceCalculator,
) : GPSVelocityCalculator {

    override fun calculateCurrentVelocity(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double {
        val distanceKM = gpsDistanceCalculator.calculateCurrentDistance(
            firstSample = firstSample,
            secondSample = secondSample,
        )

        val timeMillis = secondSample.time - firstSample.time
        val timeSeconds = timeMillis / 1000.0
        val timeMinutes = timeSeconds / 60.0
        val timeHours = timeMinutes / 60.0

        return distanceKM / timeHours
    }

    override fun calculateStackedAverageVelocity(
        currentAverageVelocity: Double,
        currentSamplesAmount: Int,
        lastSample: AnalysedSample.AnalysedGPSSample,
        nextSample: AnalysedSample.AnalysedGPSSample,
    ): Double {
        if (currentSamplesAmount <= 1) return 0.0
        val currentIntervalSpeed = calculateCurrentVelocity(
            firstSample = lastSample,
            secondSample = nextSample
        )
        return (currentAverageVelocity * (currentSamplesAmount - 2) + currentIntervalSpeed) / (currentSamplesAmount - 1)
    }

    override fun calculateAllSamplesAverageVelocity(samples: List<AnalysedSample.AnalysedGPSSample>): Double {
        if (samples.size <= 1) return 0.0

        var averageVelocity = 0.0
        samples.forEachIndexed { index, sample ->
            if (index == 0) return@forEachIndexed
            averageVelocity += calculateCurrentVelocity(firstSample = samples[index - 1], secondSample = sample)
        }

        return averageVelocity / (samples.size - 1)
    }
}