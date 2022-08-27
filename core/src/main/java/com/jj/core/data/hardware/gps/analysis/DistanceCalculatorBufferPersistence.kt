package com.jj.core.data.hardware.gps.analysis

import com.jj.core.data.hardware.gps.analysis.model.Distances
import com.jj.domain.hardware.gps.analysis.GPSDistanceCalculator
import com.jj.domain.model.analysis.AnalysedSample

// TODO Unify with VelocityCalculatorBufferPersistence
class DistanceCalculatorBufferPersistence(
    private val gpsDistanceCalculator: GPSDistanceCalculator,
) {

    private var stackedAverageDistance = 0.0
    private val collectedSamples = mutableListOf<AnalysedSample.AnalysedGPSSample>()
    private var currentSamplesAmount = 0
    private var previousGPSSample: AnalysedSample.AnalysedGPSSample? = null

    fun onNewSample(newSample: AnalysedSample.AnalysedGPSSample): Distances {
        val previousSample = prepareSampleData(newSample)
        val stackedAverageDistanceValue = stackedAverageDistance

        val currentDistance = calculateCurrentDistance(previousSample, newSample)
        val currentStackedAverageDistance = calculateStackedAverageDistance(
            previousSample = previousSample,
            newSample = newSample,
            currentAverageDistance = if (stackedAverageDistanceValue.isNaN()) 0.0 else stackedAverageDistanceValue,
            currentSamplesAmount = currentSamplesAmount
        )
        val currentAllSamplesAverageDistance = calculateAllSamplesAverageDistance(collectedSamples.toList())

        saveData(currentStackedAverageDistance = currentStackedAverageDistance)

        return Distances(
            currentDistanceKm = currentDistance,
            stackedAverageDistanceKm = currentStackedAverageDistance,
            allSamplesAverageDistanceKm = currentAllSamplesAverageDistance,
        )
    }

    private fun prepareSampleData(newSample: AnalysedSample.AnalysedGPSSample): AnalysedSample.AnalysedGPSSample? {
        val previousSample = previousGPSSample
        previousGPSSample = newSample
        currentSamplesAmount++
        collectedSamples.add(newSample)
        return previousSample
    }

    private fun saveData(currentStackedAverageDistance: Double) {
        stackedAverageDistance = currentStackedAverageDistance
    }

    private fun calculateCurrentDistance(
        previousSample: AnalysedSample.AnalysedGPSSample?,
        newSample: AnalysedSample.AnalysedGPSSample
    ): Double =
        if (previousSample != null) gpsDistanceCalculator.calculateCurrentDistance(previousSample, newSample)
        else 0.0

    private fun calculateStackedAverageDistance(
        previousSample: AnalysedSample.AnalysedGPSSample?,
        newSample: AnalysedSample.AnalysedGPSSample,
        currentAverageDistance: Double,
        currentSamplesAmount: Int
    ): Double = if (previousSample != null) {
        gpsDistanceCalculator.calculateStackedAverageDistance(
            currentAverageDistance = currentAverageDistance,
            currentSamplesAmount = currentSamplesAmount,
            lastSample = previousSample,
            nextSample = newSample,
        )
    } else {
        0.0
    }

    private fun calculateAllSamplesAverageDistance(collectedSamples: List<AnalysedSample.AnalysedGPSSample>): Double =
        gpsDistanceCalculator.calculateAllSamplesAverageDistance(collectedSamples)

    fun resetDistances(): Distances {
        currentSamplesAmount = 0
        collectedSamples.clear()
        previousGPSSample = null
        stackedAverageDistance = 0.0

        return Distances(
            currentDistanceKm = 0.0,
            stackedAverageDistanceKm = stackedAverageDistance,
            allSamplesAverageDistanceKm = 0.0,
        )
    }
}