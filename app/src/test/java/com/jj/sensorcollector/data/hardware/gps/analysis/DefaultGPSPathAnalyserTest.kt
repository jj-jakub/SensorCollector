package com.jj.sensorcollector.data.hardware.gps.analysis

import com.jj.core.data.coroutines.DefaultCoroutineScopeProvider
import com.jj.core.data.hardware.gps.analysis.DefaultGPSPathAnalyser
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import com.jj.domain.hardware.gps.repository.PathRepository
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.model.analysis.AnalysedSample
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DefaultGPSPathAnalyserTest {

    @MockK
    private lateinit var gpsRepository: GPSRepository

    @MockK
    private lateinit var gpsVelocityCalculator: GPSVelocityCalculator

    @RelaxedMockK
    private lateinit var pathRepository: PathRepository

    private val coroutineScopeProvider = DefaultCoroutineScopeProvider()

    private lateinit var defaultGPSPathAnalyser: DefaultGPSPathAnalyser

    @BeforeEach
    fun setup() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `should start observing analysed GPS samples after initialization`() {
        setupDefaultGPSPathAnalyser()

        verify(exactly = 1) { gpsRepository.collectAnalysedGPSSamples() }
    }

    @Test
    fun `should invoke pathRepository insert method after receiving analysed gps sample`() {
        val gpsSamplesFlow = flow {
            emit(AnalysedSample.AnalysedGPSSample(1.0, 1.0, 1L))
        }
        every { gpsRepository.collectAnalysedGPSSamples() } returns gpsSamplesFlow

        setupDefaultGPSPathAnalyser()

        verify(exactly = 1) { pathRepository.insertData(any()) }
    }

    private fun setupDefaultGPSPathAnalyser() {
        defaultGPSPathAnalyser = DefaultGPSPathAnalyser(gpsRepository, pathRepository, gpsVelocityCalculator, coroutineScopeProvider)
    }

    private fun setupRandomResponsesFlow() {
        val gpsSamplesFlow = flow {
            repeat(10) {
                emit(AnalysedSample.AnalysedGPSSample(1.0 + it / 10, 1.0 + it / 10, 1L + it))
            }
        }
    }
}
