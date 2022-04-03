package com.jj.sensorcollector.playground1.framework.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SensorsDataViewModel(
    private val sensorsRepository: SensorsRepository
) : ViewModel() {

    private val _accelerometerSamples = BufferedMutableSharedFlow<SensorData>()
    val accelerometerSamples = _accelerometerSamples.asSharedFlow()

    private val _gyroscopeSamples = BufferedMutableSharedFlow<SensorData>()
    val gyroscopeSamples = _gyroscopeSamples.asSharedFlow()

    private val _magneticFieldSamples = BufferedMutableSharedFlow<SensorData>()
    val magneticFieldSamples = _magneticFieldSamples.asSharedFlow()

    init {
        observeAccelerometerSamples()
        observeGyroscopeSamples()
        observeMagneticFieldSamples()
    }

    private fun observeAccelerometerSamples() {
        viewModelScope.launch {
            sensorsRepository.collectAccelerometerSamples().collect {
                if (it is SensorData.AccSample) _accelerometerSamples.tryEmit(it)
            }
        }
    }

    private fun observeGyroscopeSamples() {
        viewModelScope.launch {
            sensorsRepository.collectGyroscopeSamples().collect {
                if (it is SensorData.GyroscopeSample) _gyroscopeSamples.tryEmit(it)
            }
        }
    }

    private fun observeMagneticFieldSamples() {
        viewModelScope.launch {
            sensorsRepository.collectMagneticFieldSamples().collect {
                if (it is SensorData.MagneticFieldSample) _magneticFieldSamples.tryEmit(it)
            }
        }
    }
}