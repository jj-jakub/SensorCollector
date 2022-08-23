package com.jj.core.framework.presentation.sensors

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.data.text.VersionTextProvider
import com.jj.core.domain.managers.AnalyzerStarter
import com.jj.core.domain.managers.CameraManager
import com.jj.core.domain.monitors.SystemStateMonitor
import com.jj.domain.gps.GPSRepository
import com.jj.core.domain.repository.SensorsRepository
import com.jj.domain.model.sensors.SensorData
import com.jj.domain.model.analysis.analysis.AnalysedSample
import com.jj.core.domain.server.IPProvider
import com.jj.core.domain.ui.colors.DomainColor
import com.jj.core.domain.ui.text.TextComponent
import com.jj.core.domain.ui.text.TextCreator
import com.jj.core.framework.domain.samples.AndroidAnalysedAccUIData
import com.jj.core.framework.text.AndroidColorMapper.toDomainColor
import com.jj.core.framework.utils.BufferedMutableSharedFlow
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class SensorsDataViewModel(
    private val sensorsRepository: SensorsRepository,
    private val gpsRepository: GPSRepository,
    private val textCreator: TextCreator<AnnotatedString>,
    private val analyzerStarter: AnalyzerStarter,
    private val cameraManager: CameraManager,
    ipProvider: IPProvider,
    versionTextProvider: VersionTextProvider,
    systemStateMonitor: SystemStateMonitor
) : ViewModel() {

    private val _analysedAccelerometerSampleString = BufferedMutableSharedFlow<AndroidAnalysedAccUIData<AnnotatedString>>()
    val analysedAccelerometerSampleString = _analysedAccelerometerSampleString.asSharedFlow()

    private val _gyroscopeSamples = BufferedMutableSharedFlow<SensorData>()
    val gyroscopeSamples = _gyroscopeSamples.asSharedFlow()

    private val _magneticFieldSamples = BufferedMutableSharedFlow<SensorData>()
    val magneticFieldSamples = _magneticFieldSamples.asSharedFlow()

    private val _gpsSamples = BufferedMutableSharedFlow<SensorData>()
    val gpsSamples = _gpsSamples.asSharedFlow()

    val versionInfoText = versionTextProvider.getAboutVersionText()
    val ipAddressText = ipProvider.getIPAddress()

    val accelerometerCollectionState = systemStateMonitor.accelerometerCollectionState
    val gyroscopeCollectionState = systemStateMonitor.gyroscopeCollectionState
    val magneticFieldCollectionState = systemStateMonitor.magneticFieldCollectionState
    val gpsCollectionState = systemStateMonitor.gpsCollectionState

    init {
        observeAccelerometerSamples()
        observeGyroscopeSamples()
        observeMagneticFieldSamples()
        observeGPSSamples()
    }

    fun onStartAccelerometerClick() {
        analyzerStarter.startPermanentAccelerometerAnalysis()
    }

    fun onStopAccelerometerClick() {
        analyzerStarter.stopPermanentAccelerometerAnalysis()
    }

    fun onTakePhotoClick() {
        viewModelScope.launch {
            cameraManager.takePhoto()
        }
    }

    fun registerCameraPreview(preview: androidx.camera.core.Preview) {
        cameraManager.registerCameraPreview(preview)
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

    private fun createColoredSpannable(analysedSample: AnalysedSample.AnalysedAccSample): AnnotatedString =
        textCreator.buildColoredString(
            TextComponent("X: ", DomainColor.Default),
            TextComponent("%.${3}f".format(analysedSample.analysedX.value), analysedSample.analysedX.analysisResult.toDomainColor()),
            TextComponent(" Y: ", DomainColor.Default),
            TextComponent("%.${3}f".format(analysedSample.analysedY.value), analysedSample.analysedY.analysisResult.toDomainColor()),
            TextComponent(" Z: ", DomainColor.Default),
            TextComponent("%.${3}f".format(analysedSample.analysedZ.value), analysedSample.analysedZ.analysisResult.toDomainColor())
        )

    private fun observeGyroscopeSamples() {
        viewModelScope.launch {
            delay(5000L) // Debug
            sensorsRepository.collectGyroscopeSamples().collect {
                if (it is SensorData.GyroscopeSample) _gyroscopeSamples.tryEmit(it)
            }
        }
    }

    private fun observeMagneticFieldSamples() {
        viewModelScope.launch {
            delay(10000L) // Debug
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