package com.jj.sensorcollector.playground1.data.samples.gps

import android.location.Location
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class DefaultGPSVelocityCalculatorTest {

    private lateinit var defaultGPSVelocityCalculator: DefaultGPSVelocityCalculator

    @BeforeEach
    fun setup() {
        defaultGPSVelocityCalculator = DefaultGPSVelocityCalculator()
    }

    @Test
    fun a() {
        val firstSample = AnalysedSample.AnalysedGPSSample(1.0, 1.0, 1)
        val secondSample = AnalysedSample.AnalysedGPSSample(1.1, 1.1, 1)
        println(
            defaultGPSVelocityCalculator.calculateVelocity(
                firstSample = firstSample,
                secondSample = secondSample
            )
        )
    }
}

