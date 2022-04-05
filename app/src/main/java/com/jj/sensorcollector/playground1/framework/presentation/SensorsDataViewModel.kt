package com.jj.sensorcollector.playground1.framework.presentation

import android.text.Spannable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.sensorcollector.framework.utils.BufferedMutableSharedFlow
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.repository.SensorsRepository
import com.jj.sensorcollector.playground1.domain.samples.AnalysedSample
import com.jj.sensorcollector.playground1.domain.ui.DomainColor
import com.jj.sensorcollector.playground1.domain.ui.text.TextComponent
import com.jj.sensorcollector.playground1.domain.ui.text.TextCreator
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toDomainColor
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class SensorsDataViewModel(
    private val sensorsRepository: SensorsRepository,
    private val textCreator: TextCreator<Spannable>
) : ViewModel() {

//    private val _accelerometerSamples = BufferedMutableSharedFlow<UISample.AccelerometerUISample>()
//    val accelerometerSamples = _accelerometerSamples.asSharedFlow()

    private val _accelerometerSamples = BufferedMutableSharedFlow<Spannable>()
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
            sensorsRepository.collectAnalysedAccelerometerSamples().collect {
                if (it is AnalysedSample.AnalysedAccSample) {
                    _accelerometerSamples.tryEmit(
                        textCreator.buildColoredString(
                            TextComponent("X: ", DomainColor.Default),
                            TextComponent(it.analysedX.value.toString(), it.analysedX.analysisResult.toDomainColor()),
                            TextComponent(" Y: ", DomainColor.Default),
                            TextComponent(it.analysedY.value.toString(), it.analysedY.analysisResult.toDomainColor()),
                            TextComponent(" Z: ", DomainColor.Default),
                            TextComponent(it.analysedZ.value.toString(), it.analysedZ.analysisResult.toDomainColor())
                        )
                    )
                }
            }
        }
    }

//    private fun createAccUiSample(analysedSample: AnalysedSample.AnalysedAccSample) =
//        UISample.AccelerometerUISample(
//            x = textCreator.buildColoredString(
//                TextComponent("X: ", DomainColor.Default),
//                TextComponent(it.analysedX.toString(), it.analysedX.analysisResult.toDomainColor())
//            ),
//            y = textCreator.buildColoredString(
//                TextComponent("Y: ", DomainColor.Default),
//                TextComponent(it.analysedY.toString(), it.analysedY.analysisResult.toDomainColor())
//            ),
//            z = textCreator.buildColoredString(
//                TextComponent("Z: ", DomainColor.Default),
//                TextComponent(it.analysedZ.toString(), it.analysedZ.analysisResult.toDomainColor())
//            )
//        )

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