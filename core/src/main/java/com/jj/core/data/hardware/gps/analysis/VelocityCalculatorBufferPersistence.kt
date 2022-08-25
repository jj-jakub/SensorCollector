package com.jj.core.data.hardware.gps.analysis

import com.jj.core.data.hardware.gps.analysis.model.Velocities
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import com.jj.domain.model.analysis.AnalysedSample

class VelocityCalculatorBufferPersistence(
    private val gpsVelocityCalculator: GPSVelocityCalculator,
) {

    private var stackedAverageVelocity = 0.0
    private val collectedSamples = mutableListOf<AnalysedSample.AnalysedGPSSample>()
    private var currentSamplesAmount = 0
    private var previousGPSSample: AnalysedSample.AnalysedGPSSample? = null

    fun onNewSample(newSample: AnalysedSample.AnalysedGPSSample): Velocities {
        val previousSample = prepareSampleData(newSample)
        val stackedAverageVelocityValue = stackedAverageVelocity

        val currentVelocity = calculateCurrentVelocity(previousSample, newSample)
        val currentStackedAverageVelocity = calculateStackedAverageVelocity(
            previousSample = previousSample,
            newSample = newSample,
            currentAverageVelocity = if (stackedAverageVelocityValue.isNaN()) 0.0 else stackedAverageVelocityValue,
            currentSamplesAmount = currentSamplesAmount
        )
        val currentAllSamplesAverageVelocity = calculateAllSamplesAverageVelocity(collectedSamples.toList())

        saveData(currentStackedAverageVelocity = currentStackedAverageVelocity)

        return Velocities(
            currentVelocity = currentVelocity,
            stackedAverageVelocity = currentStackedAverageVelocity,
            allSamplesAverageVelocity = currentAllSamplesAverageVelocity,
        )
    }

    private fun prepareSampleData(newSample: AnalysedSample.AnalysedGPSSample): AnalysedSample.AnalysedGPSSample? {
        val previousSample = previousGPSSample
        previousGPSSample = newSample
        currentSamplesAmount++
        collectedSamples.add(newSample)
        return previousSample
    }

    private fun saveData(currentStackedAverageVelocity: Double) {
        stackedAverageVelocity = currentStackedAverageVelocity
    }

    private fun calculateCurrentVelocity(
        previousSample: AnalysedSample.AnalysedGPSSample?,
        newSample: AnalysedSample.AnalysedGPSSample
    ): Double =
        if (previousSample != null) gpsVelocityCalculator.calculateCurrentVelocity(previousSample, newSample)
        else 0.0

    private fun calculateStackedAverageVelocity(
        previousSample: AnalysedSample.AnalysedGPSSample?,
        newSample: AnalysedSample.AnalysedGPSSample,
        currentAverageVelocity: Double,
        currentSamplesAmount: Int
    ): Double = if (previousSample != null) {
        gpsVelocityCalculator.calculateStackedAverageVelocity(
            currentAverageVelocity = currentAverageVelocity,
            currentSamplesAmount = currentSamplesAmount,
            lastSample = previousSample,
            nextSample = newSample,
        )
    } else {
        0.0
    }

    private fun calculateAllSamplesAverageVelocity(collectedSamples: List<AnalysedSample.AnalysedGPSSample>): Double =
        gpsVelocityCalculator.calculateAllSamplesAverageVelocity(collectedSamples)

    fun resetVelocities(): Velocities {
        currentSamplesAmount = 0
        collectedSamples.clear()
        previousGPSSample = null
        return onNewSample(
            newSample = AnalysedSample.AnalysedGPSSample(0.0, 0.0, 0)
        )
    }
}