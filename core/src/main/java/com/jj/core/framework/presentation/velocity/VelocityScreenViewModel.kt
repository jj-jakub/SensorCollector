package com.jj.core.framework.presentation.velocity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.data.hardware.gps.analysis.VelocityCalculatorBufferPersistence
import com.jj.core.data.hardware.gps.analysis.model.Velocities
import com.jj.domain.base.usecase.invoke
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.gps.usecase.StartGPSAnalysis
import com.jj.domain.hardware.gps.usecase.StopGPSAnalysis
import com.jj.domain.monitoring.GPSStateMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class VelocityScreenViewModel(
    gpsStateMonitor: GPSStateMonitor,
    private val gpsRepository: GPSRepository,
    private val velocityCalculatorBufferPersistence: VelocityCalculatorBufferPersistence,
    private val startGPSAnalysis: StartGPSAnalysis,
    private val stopGPSAnalysis: StopGPSAnalysis,
) : ViewModel() {

    val gpsState = gpsStateMonitor.sampleCollectionState

    private val _currentVelocity = MutableStateFlow(0.0)
    val currentVelocity = _currentVelocity.asStateFlow()

    private val _averageVelocity1 = MutableStateFlow(0.0)
    val averageVelocity1 = _averageVelocity1.asStateFlow()

    private val _averageVelocity2 = MutableStateFlow(0.0)
    val averageVelocity2 = _averageVelocity2.asStateFlow()

    init {
        viewModelScope.launch {
            collectGPSSamples()
        }
    }

    private suspend fun collectGPSSamples() {
        gpsRepository.collectAnalysedGPSSamples().collect { newSample ->
            val velocities = velocityCalculatorBufferPersistence.onNewSample(newSample = newSample)
            processVelocities(velocities = velocities)
        }
    }

    private fun processVelocities(velocities: Velocities) {
        _currentVelocity.value = velocities.currentVelocity
        _averageVelocity1.value = velocities.stackedAverageVelocity
        _averageVelocity2.value = velocities.allSamplesAverageVelocity

    }

    fun onClearVelocitiesClick() {
        val velocities = velocityCalculatorBufferPersistence.resetVelocities()
        processVelocities(velocities = velocities)
    }

    fun onPermissionGranted() {
        viewModelScope.launch { startGPSAnalysis() }
    }

    fun onStartGPSAnalysisClick() {
        viewModelScope.launch { startGPSAnalysis() }
    }

    fun onStopGPSAnalysisClick() {
        viewModelScope.launch { stopGPSAnalysis() }
    }
}