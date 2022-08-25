package com.jj.sensorcollector.data.hardware.gps.analysis

import com.jj.core.data.hardware.gps.analysis.HaversineGPSVelocityCalculator
import com.jj.domain.model.analysis.AnalysedSample
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class HaversineGPSVelocityCalculatorTest {

    private lateinit var haversineGPSVelocityCalculator: HaversineGPSVelocityCalculator

    @BeforeEach
    fun setup() {
        haversineGPSVelocityCalculator = HaversineGPSVelocityCalculator()
    }

    @Test
    fun a() {
        val firstSample = AnalysedSample.AnalysedGPSSample(1.0, 1.0, 1000)
        val secondSample = AnalysedSample.AnalysedGPSSample(1.0, 1.01, 2000)
        println(
            haversineGPSVelocityCalculator.calculateCurrentVelocity(
                firstSample = firstSample,
                secondSample = secondSample
            )
        )
    }
}

