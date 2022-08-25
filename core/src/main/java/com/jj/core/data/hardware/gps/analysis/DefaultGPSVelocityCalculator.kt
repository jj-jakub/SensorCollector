package com.jj.core.data.hardware.gps.analysis

import android.util.Log
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import kotlin.math.asin
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

class DefaultGPSVelocityCalculator : GPSVelocityCalculator {

    override fun calculateAverageVelocity(
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

        Log.d("ABABV", "distanceKM: $distanceKM, timeHours: $timeHours, timeMillis: $timeMillis")
        return distanceKM / timeHours
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