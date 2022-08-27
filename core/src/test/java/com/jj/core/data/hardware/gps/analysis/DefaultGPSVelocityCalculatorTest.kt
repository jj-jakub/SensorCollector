package com.jj.core.data.hardware.gps.analysis

import com.jj.core.data.hardware.gps.analysis.VelocityTestUtils.Companion.FINAL_AVERAGE_VELOCITY
import com.jj.core.data.hardware.gps.analysis.VelocityTestUtils.Companion.analysedGPSSamples
import com.jj.core.data.hardware.gps.analysis.VelocityTestUtils.Companion.averageVelocityTestingSet
import com.jj.core.data.hardware.gps.analysis.VelocityTestUtils.Companion.velocityTolerance
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.test.assertEquals

class DefaultGPSVelocityCalculatorTest {

    private lateinit var defaultGPSVelocityCalculator: DefaultGPSVelocityCalculator

    private lateinit var haversineGPSDistanceCalculator: HaversineGPSDistanceCalculator

    @BeforeEach
    fun setup() {
        haversineGPSDistanceCalculator = HaversineGPSDistanceCalculator()
        defaultGPSVelocityCalculator = DefaultGPSVelocityCalculator(gpsDistanceCalculator = haversineGPSDistanceCalculator)
    }

    @ParameterizedTest
    @MethodSource("currentVelocityTestingSet")
    fun `calculate current velocity from two samples`(currentVelocityTestingParam: VelocityTestUtils.CurrentVelocityTestingParam) {
        assertEquals(
            expected = currentVelocityTestingParam.currentVelocity,
            actual = defaultGPSVelocityCalculator.calculateCurrentVelocity(
                firstSample = currentVelocityTestingParam.firstSample,
                secondSample = currentVelocityTestingParam.secondSample
            )
        )
    }

    @Test
    fun `calculate average velocity from all samples one by one`() {
        var currentAverageVelocity = 0.0
        averageVelocityTestingSet.forEachIndexed { index, averageVelocityTestingParam ->
            if (index == 0) return@forEachIndexed
            currentAverageVelocity = defaultGPSVelocityCalculator.calculateStackedAverageVelocity(
                currentAverageVelocity = currentAverageVelocity,
                currentSamplesAmount = index + 1,
                lastSample = averageVelocityTestingSet[index - 1].sample,
                nextSample = averageVelocityTestingParam.sample
            )
            assertEquals(averageVelocityTestingParam.averageVelocity, currentAverageVelocity, absoluteTolerance = velocityTolerance)
        }
        assertEquals(FINAL_AVERAGE_VELOCITY, currentAverageVelocity, absoluteTolerance = velocityTolerance)
    }

    @Test
    fun `calculate velocity from list of samples`() {
        val calculatedAverageVelocity = defaultGPSVelocityCalculator.calculateAllSamplesAverageVelocity(analysedGPSSamples)
        assertEquals(FINAL_AVERAGE_VELOCITY, calculatedAverageVelocity)
    }

    companion object {
        @JvmStatic
        fun currentVelocityTestingSet() = VelocityTestUtils.currentVelocityTestingSet()
    }
}

