package com.jj.core.framework.presentation.velocity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.domain.hardware.gps.analysis.GPSVelocityCalculator
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.monitoring.GPSStateMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VelocityScreenViewModel(
    gpsStateMonitor: GPSStateMonitor,
    private val gpsRepository: GPSRepository,
    private val gpsVelocityCalculator: GPSVelocityCalculator,
) : ViewModel() {

    val gpsState = gpsStateMonitor.sampleCollectionState

    private val collectedSamples = mutableListOf<AnalysedSample.AnalysedGPSSample>()
    private var calculatedSamplesAmount = 0

    private val _currentVelocity = MutableStateFlow(0.0)
    val currentVelocity = _currentVelocity.asStateFlow()

    private val _averageVelocity1 = MutableStateFlow(0.0)
    val averageVelocity1 = _averageVelocity1.asStateFlow()

    private val _averageVelocity2 = MutableStateFlow(0.0)
    val averageVelocity2 = _averageVelocity2.asStateFlow()

    private var previousGPSSample: AnalysedSample.AnalysedGPSSample? = null

    init {
        viewModelScope.launch {
            collectGPSSamples()
        }
    }

    private suspend fun collectGPSSamples() {
        gpsRepository.collectAnalysedGPSSamples().collect { newSample ->
            val previousSample = previousGPSSample

            if (previousSample != null) {
                val currentVelocity = gpsVelocityCalculator.calculateCurrentVelocity(firstSample = previousSample, secondSample = newSample)
                calculateVelocities(currentVelocity, previousSample, newSample)
                previousGPSSample = newSample
            }

            previousGPSSample = newSample
            calculatedSamplesAmount++
            collectedSamples.add(newSample)
        }
    }

    private fun calculateVelocities(
        currentVelocity: Double,
        previousSample: AnalysedSample.AnalysedGPSSample,
        newSample: AnalysedSample.AnalysedGPSSample
    ) {
        _currentVelocity.value = currentVelocity
        val averageVelocity1 = _averageVelocity1.value
        _averageVelocity1.value = gpsVelocityCalculator.calculateAverageVelocity(
            if (averageVelocity1.isNaN()) 0.0 else averageVelocity1,
            currentSamplesAmount = calculatedSamplesAmount,
            previousSample,
            newSample
        )
        _averageVelocity2.value = gpsVelocityCalculator.calculateAverageVelocity(
            samples = collectedSamples
        )
    }

    fun onClearVelocitiesClick() {
        calculatedSamplesAmount = 0
        collectedSamples.clear()
        calculateVelocities(
            currentVelocity = 0.0,
            previousSample = AnalysedSample.AnalysedGPSSample(0.0, 0.0, 0),
            newSample = AnalysedSample.AnalysedGPSSample(0.0, 0.0, 0)
        )
    }
}