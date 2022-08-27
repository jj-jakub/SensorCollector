package com.jj.core.data.hardware.gps.analysis

import com.jj.domain.hardware.gps.analysis.GPSDistanceCalculator
import com.jj.domain.model.analysis.AnalysedSample
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class HaversineGPSDistanceCalculator: GPSDistanceCalculator {
    override fun calculateCurrentDistance(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double = getHaversineDistanceKM(
        firstLat = firstSample.latitude,
        firstLon = firstSample.longitude,
        secondLat = secondSample.latitude,
        secondLon = secondSample.longitude
    )

    override fun calculateStackedDistance(
        currentDistance: Double,
        lastSample: AnalysedSample.AnalysedGPSSample,
        nextSample: AnalysedSample.AnalysedGPSSample
    ): Double {
        val currentIntervalDistance = calculateCurrentDistance(
            firstSample = lastSample,
            secondSample = nextSample
        )
        return currentDistance + currentIntervalDistance
    }

    override fun calculateAllSamplesDistance(samples: List<AnalysedSample.AnalysedGPSSample>): Double {
        if (samples.size <= 1) return 0.0

        var distance = 0.0
        samples.forEachIndexed { index, sample ->
            if (index == 0) return@forEachIndexed
            distance += calculateCurrentDistance(firstSample = samples[index - 1], secondSample = sample)
        }

        return distance
    }

    /** @return Distance in KM */
    private fun getHaversineDistanceKM(firstLat: Double, firstLon: Double, secondLat: Double, secondLon: Double): Double {
        val rad = 6372.8 //Earth's Radius In kilometers
        val dLat = Math.toRadians(secondLat - firstLat)
        val dLon = Math.toRadians(secondLon - firstLon)
        val firstLatRadians = Math.toRadians(firstLat)
        val secondLatRadians = Math.toRadians(secondLat)
        val a = sin(dLat / 2) * sin(dLat / 2) + sin(dLon / 2) * sin(dLon / 2) * cos(firstLatRadians) * cos(secondLatRadians)
        val c = 2 * asin(sqrt(a))
        return rad * c
    }
}