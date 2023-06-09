package com.jj.core.framework.presentation.sensors

import androidx.compose.ui.text.AnnotatedString
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jj.core.data.text.VersionTextProvider
import com.jj.domain.monitoring.SystemStateMonitor
import com.jj.domain.server.IPProvider
import com.jj.domain.ui.colors.DomainColor
import com.jj.domain.ui.text.TextComponent
import com.jj.domain.ui.text.TextCreator
import com.jj.core.framework.data.analysis.AndroidAnalysedAccUIData
import com.jj.core.framework.data.hardware.camera.CameraManager
import com.jj.core.framework.text.AndroidColorMapper.toDomainColor
import com.jj.domain.utils.BufferedMutableSharedFlow
import com.jj.domain.utils.shouldStartNewJob
import com.jj.domain.base.usecase.UseCase
import com.jj.domain.base.usecase.invoke
import com.jj.domain.model.analysis.AnalysedSample
import com.jj.domain.hardware.accelerometer.usecase.StartAccelerometerAnalysis
import com.jj.domain.hardware.accelerometer.usecase.StopAccelerometerAnalysis
import com.jj.domain.hardware.general.SensorsRepository
import com.jj.domain.hardware.gps.repository.GPSRepository
import com.jj.domain.hardware.gyroscope.usecase.StartGyroscopeCollection
import com.jj.domain.hardware.magneticfield.usecase.StartMagneticFieldCollection
import com.jj.domain.hardware.model.SensorData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SensorsDataViewModel(
    private val sensorsRepository: SensorsRepository,
    private val textCreator: TextCreator<AnnotatedString>,
    private val startAccelerometerAnalysis: StartAccelerometerAnalysis,
    private val stopAccelerometerAnalysis: StopAccelerometerAnalysis,
    private val startGyroscopeCollection: StartGyroscopeCollection,
    private val startMagneticFieldCollection: StartMagneticFieldCollection,
    private val gpsRepository: GPSRepository,
    private val cameraManager: CameraManager,
    ipProvider: IPProvider,
    versionTextProvider: VersionTextProvider,
    systemStateMonitor: SystemStateMonitor,
) : ViewModel() {

    private val _analysedAccelerometerSampleString = BufferedMutableSharedFlow<AndroidAnalysedAccUIData<AnnotatedString>>()
    val analysedAccelerometerSampleString = _analysedAccelerometerSampleString.asSharedFlow()

    private var gyroscopeCollectingJob: Job? = null
    private val _gyroscopeSamples = BufferedMutableSharedFlow<SensorData>()
    val gyroscopeSamples = _gyroscopeSamples.asSharedFlow()

    private var magneticFieldCollectingJob: Job? = null
    private val _magneticFieldSamples = BufferedMutableSharedFlow<SensorData>()
    val magneticFieldSamples = _magneticFieldSamples.asSharedFlow()

    private var gpsCollectingJob: Job? = null
    private val _gpsSamples = BufferedMutableSharedFlow<AnalysedSample.AnalysedGPSSample>()
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
        viewModelScope startUseCase startAccelerometerAnalysis
    }

    fun onStopAccelerometerClick() {
        viewModelScope startUseCase stopAccelerometerAnalysis
    }

    fun onStartGyroscopeClick() {
        observeGyroscopeSamples()
    }

    fun onStopGyroscopeClick() {
        gyroscopeCollectingJob?.cancel()
        gyroscopeCollectingJob = null
    }

    fun onStartMagneticFieldClick() {
        observeMagneticFieldSamples()
    }

    fun onStopMagneticFieldClick() {
        magneticFieldCollectingJob?.cancel()
        magneticFieldCollectingJob = null
    }

    fun onStartGPSClick() {
        observeGPSSamples()
    }

    fun onStopGPSClick() {
        gpsCollectingJob?.cancel()
        gpsCollectingJob = null
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
        if (gyroscopeCollectingJob.shouldStartNewJob()) {
            gyroscopeCollectingJob = viewModelScope.launch {
                startGyroscopeCollection().collectLatest {
                    _gyroscopeSamples.emit(it)
                }
            }
        }
    }

    private fun observeMagneticFieldSamples() {
        if (magneticFieldCollectingJob.shouldStartNewJob()) {
            magneticFieldCollectingJob = viewModelScope.launch {
                startMagneticFieldCollection().collectLatest {
                    _magneticFieldSamples.emit(it)
                }
            }
        }
    }

    private fun observeGPSSamples() {
        if (gpsCollectingJob.shouldStartNewJob()) {
            gpsCollectingJob = viewModelScope.launch {
                gpsRepository.collectAnalysedGPSSamples().collectLatest {
                    _gpsSamples.emit(it)
                }
            }
        }
    }

    private infix fun CoroutineScope.startUseCase(useCase: UseCase<Unit, *>): Job = this.launch { useCase() }
}