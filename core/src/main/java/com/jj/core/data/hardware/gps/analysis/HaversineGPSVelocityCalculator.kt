package com.jj.core.data.hardware.gps.analysis

import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class HaversineGPSVelocityCalculator : GPSVelocityCalculator {

    override fun calculateCurrentVelocity(
        firstSample: AnalysedSample.AnalysedGPSSample,
        secondSample: AnalysedSample.AnalysedGPSSample
    ): Double {
        val distanceKM = getHaversineDistanceKM(
            firstLat = firstSample.latitude,
            firstLon = firstSample.longitude,
            secondLat = secondSample.latitude,
            secondLon = secondSample.longitude
        )

        val timeMillis = secondSample.time - firstSample.time
        val timeSeconds = timeMillis / 1000.0
        val timeMinutes = timeSeconds / 60.0
        val timeHours = timeMinutes / 60.0

//        Log.d("ABABV", "distanceKM: $distanceKM, timeHours: $timeHours, timeMillis: $timeMillis")
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