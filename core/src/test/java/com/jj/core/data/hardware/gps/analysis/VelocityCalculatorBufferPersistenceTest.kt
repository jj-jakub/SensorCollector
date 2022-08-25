package com.jj.core.data.hardware.gps.analysis

import com.jj.core.data.hardware.gps.analysis.VelocityTestUtils.Companion.averageVelocityTestingSet
import com.jj.core.data.hardware.gps.analysis.VelocityTestUtils.Companion.velocityTolerance
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import com.jj.domain.model.analysis.AnalysedSample
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class VelocityCalculatorBufferPersistenceTest {

    private lateinit var velocityCalculatorBufferPersistence: VelocityCalculatorBufferPersistence

    private lateinit var gpsVelocityCalculator: GPSVelocityCalculator

    @BeforeEach
    fun setup() {
        gpsVelocityCalculator = HaversineGPSVelocityCalculator()
        velocityCalculatorBufferPersistence = VelocityCalculatorBufferPersistence(gpsVelocityCalculator = gpsVelocityCalculator)
    }

    @Test
    fun `currentVelocity returned after first sample should be equal to 0`() {
        val param = averageVelocityTestingSet.first()
        val velocities = velocityCalculatorBufferPersistence.onNewSample(param.sample)
        assertEquals(0.0, velocities.currentVelocity)
    }

    @Test
    fun `stacked velocity returned after first sample should be equal to averageVelocity`() {
        averageVelocityTestingSet.forEach {
            val velocities = velocityCalculatorBufferPersistence.onNewSample(it.sample)
            assertEquals(it.averageVelocity, velocities.stackedAverageVelocity, absoluteTolerance = velocityTolerance)
        }
    }
}