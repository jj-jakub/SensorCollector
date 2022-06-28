package com.jj.core.framework.presentation

import android.content.Context
import android.util.Log
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.data.text.VersionTextProvider
import com.jj.core.domain.managers.AnalyzerStarter
import com.jj.core.domain.managers.CameraManager
import com.jj.core.domain.monitors.SystemStateMonitor
import com.jj.core.domain.repository.GPSRepository
import com.jj.core.domain.repository.SensorsRepository
import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.samples.analysis.AnalysedSample
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
import java.util.concurrent.Executors

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

    private val _analysedAccelerometerSampleString = BufferedMutableSharedFlow<AndroidAnalysedAccUIData>()
    val analysedAccelerometerSampleString = _analysedAccelerometerSampleString.asSharedFlow()

    private val _gyroscopeSamples = BufferedMutableSharedFlow<SensorData>()
    val gyroscopeSamples = _gyroscopeSamples.asSharedFlow()

    private val _magneticFieldSamples = BufferedMutableSharedFlow<SensorData>()
    val magneticFieldSamples = _magneticFieldSamples.asSharedFlow()

    private val _gpsSamples = BufferedMutableSharedFlow<SensorData>()
    val gpsSamples = _gpsSamples.asSharedFlow()

    val versionInfoText = versionTextProvider.getAboutVersionText()
    val ipAddressText = ipProvider.getIPAddress()
    val imageCapture = ImageCapture.Builder().build()

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


    private val callback = object : ImageCapture.OnImageCapturedCallback() {
        override fun onCaptureSuccess(image: ImageProxy) {
            super.onCaptureSuccess(image)
            Log.d("ABAB", "Success")
        }

        override fun onError(exception: ImageCaptureException) {
            super.onError(exception)
            Log.d("ABAB", "Error")
            exception.printStackTrace()
        }
    }

    fun onTakePhotoClick(context: Context) {
        imageCapture.takePicture(Executors.newSingleThreadExecutor(), callback)
        cameraManager.takePhoto(context)
    }

    private fun observeAccelerometerSamples() {
        viewModelScope.launch {
            sensorsRepository.collectAnalysedAccelerometerSamples().collect {
                val coloredSpannable = createColoredSpannable(it)
                val uiData = AndroidAnalysedAccUIData(it, coloredSpannable.toString())
                _analysedAccelerometerSampleString.tryEmit(uiData)
            }
        }
    }

    private fun createColoredSpannable(analysedSample: AnalysedSample.AnalysedAccSample): AnnotatedString =
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