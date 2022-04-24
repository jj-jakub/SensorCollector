package com.jj.sensorcollector.playground1.framework.presentation

import android.text.Spannable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import com.jj.sensorcollector.playground1.domain.monitors.SystemStateMonitor
import com.jj.sensorcollector.playground1.domain.repository.GPSRepository
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.ui.colors.DomainColor
import com.jj.sensorcollector.playground1.domain.ui.text.TextComponent
import com.jj.sensorcollector.playground1.domain.ui.text.TextCreator
import com.jj.sensorcollector.playground1.framework.domain.ui.samples.AndroidAnalysedAccUIData
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toDomainColor
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SensorsDataViewModel(
    private val sensorsRepository: SensorsRepository,
    private val gpsRepository: GPSRepository,
    private val textCreator: TextCreator<Spannable>,
    systemStateMonitor: SystemStateMonitor
) : ViewModel() {

    private val _analysedAccelerometerSampleString = BufferedMutableSharedFlow<AndroidAnalysedAccUIData>()
    val analysedAccelerometerSampleString = _analysedAccelerometerSampleString.asSharedFlow()

    private val _gyroscopeSamples = BufferedMutableSharedFlow<SensorData>()
    val gyroscopeSamples = _gyroscopeSamples.asSharedFlow()

    private val _magneticFieldSamples = BufferedMutableSharedFlow<SensorData>()
    val magneticFieldSamples = _magneticFieldSamples.asSharedFlow()

    private val _gpsSamples = BufferedMutableSharedFlow<SensorData>()
    val gpsSamples = _gpsSamples.asSharedFlow()

    val accelerometerCollectionState = systemStateMonitor.accelerometerCollectionState

    init {
        observeAccelerometerSamples()
        observeGyroscopeSamples()
        observeMagneticFieldSamples()
        observeGPSSamples()
    }

    private fun observeAccelerometerSamples() {
        viewModelScope.launch {
            sensorsRepository.collectAnalysedAccelerometerSamples().collect {
                val coloredSpannable = createColoredSpannable(it)
                val uiData = AndroidAnalysedAccUIData(it, coloredSpannable)
                _analysedAccelerometerSampleString.tryEmit(uiData)
            }
        }
    }

    private fun createColoredSpannable(analysedSample: AnalysedSample.AnalysedAccSample): Spannable =
        textCreator.buildColoredString(
            TextComponent("X: ", DomainColor.Default),
            TextComponent(analysedSample.analysedX.value.toString(), analysedSample.analysedX.analysisResult.toDomainColor()),
            TextComponent(" Y: ", DomainColor.Default),
            TextComponent(analysedSample.analysedY.value.toString(), analysedSample.analysedY.analysisResult.toDomainColor()),
            TextComponent(" Z: ", DomainColor.Default),
            TextComponent(analysedSample.analysedZ.value.toString(), analysedSample.analysedZ.analysisResult.toDomainColor())
        )

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

    private fun observeGPSSamples() {
        viewModelScope.launch {
            gpsRepository.collectGPSSamples().collect {
                if (it is SensorData.GPSSample || it is SensorData.Error) {
                    _gpsSamples.tryEmit(it)
                }
            }
        }
    }
}