package com.jj.sensorcollector.playground1.data.samples.gps

import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.repository.PathRepository
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
class DefaultGPSPathAnalyserTest {

    @MockK
    private lateinit var gpsRepository: GPSRepository

    @RelaxedMockK
    private lateinit var pathRepository: PathRepository

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
        defaultGPSPathAnalyser = DefaultGPSPathAnalyser(gpsRepository, pathRepository)
    }

    private fun setupRandomResponsesFlow() {
        val gpsSamplesFlow = flow {
            repeat(10) {
                emit(AnalysedSample.AnalysedGPSSample(1.0 + it/10, 1.0 + it/10, 1L + it))
            }
        }
    }
}
