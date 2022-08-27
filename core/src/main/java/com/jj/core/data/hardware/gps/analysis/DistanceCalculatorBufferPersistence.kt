package com.jj.core.data.hardware.gps.analysis

import com.jj.core.data.hardware.gps.analysis.model.Distances
import com.jj.domain.hardware.gps.analysis.GPSDistanceCalculator
import com.jj.domain.model.analysis.AnalysedSample

// TODO Unify with VelocityCalculatorBufferPersistence
class DistanceCalculatorBufferPersistence(
    private val gpsDistanceCalculator: GPSDistanceCalculator,
) {

    private var stackedDistance = 0.0
    private val collectedSamples = mutableListOf<AnalysedSample.AnalysedGPSSample>()
    private var previousGPSSample: AnalysedSample.AnalysedGPSSample? = null

    fun onNewSample(newSample: AnalysedSample.AnalysedGPSSample): Distances {
        val previousSample = prepareSampleData(newSample)
        val stackedDistanceValue = stackedDistance

        val currentDistance = calculateCurrentDistance(previousSample, newSample)
        val currentStackedDistance = calculateStackedDistance(
            previousSample = previousSample,
            newSample = newSample,
            currentDistance = if (stackedDistanceValue.isNaN()) 0.0 else stackedDistanceValue,
        )
        val currentAllSamplesDistance = calculateAllSamplesDistance(collectedSamples.toList())

        saveData(currentStackedDistance = currentStackedDistance)

        return Distances(
            currentIntervalDistanceKm = currentDistance,
            stackedDistanceKm = currentStackedDistance,
            allSamplesDistanceKm = currentAllSamplesDistance,
        )
    }

    private fun prepareSampleData(newSample: AnalysedSample.AnalysedGPSSample): AnalysedSample.AnalysedGPSSample? {
        val previousSample = previousGPSSample
        previousGPSSample = newSample
        collectedSamples.add(newSample)
        return previousSample
    }

    private fun saveData(currentStackedDistance: Double) {
        stackedDistance = currentStackedDistance
    }

    private fun calculateCurrentDistance(
        previousSample: AnalysedSample.AnalysedGPSSample?,
        newSample: AnalysedSample.AnalysedGPSSample
    ): Double =
        if (previousSample != null) gpsDistanceCalculator.calculateCurrentDistance(previousSample, newSample)
        else 0.0

    private fun calculateStackedDistance(
        previousSample: AnalysedSample.AnalysedGPSSample?,
        newSample: AnalysedSample.AnalysedGPSSample,
        currentDistance: Double,
    ): Double = if (previousSample != null) {
        gpsDistanceCalculator.calculateStackedDistance(
            currentDistance = currentDistance,
            lastSample = previousSample,
            nextSample = newSample,
        )
    } else {
        0.0
    }

    private fun calculateAllSamplesDistance(collectedSamples: List<AnalysedSample.AnalysedGPSSample>): Double =
        gpsDistanceCalculator.calculateAllSamplesDistance(collectedSamples)

    fun resetDistances(): Distances {
        collectedSamples.clear()
        previousGPSSample = null
        stackedDistance = 0.0

        return Distances(
            currentIntervalDistanceKm = 0.0,
            stackedDistanceKm = stackedDistance,
            allSamplesDistanceKm = 0.0,
        )
    }
}