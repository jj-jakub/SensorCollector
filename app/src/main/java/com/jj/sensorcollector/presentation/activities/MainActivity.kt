package com.jj.sensorcollector.presentation.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.lifecycleScope
import com.jj.design.YoScreen
import com.jj.sensorcollector.data.text.VersionTextProvider
import com.jj.sensorcollector.databinding.ActivityMainBinding
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.ui.colors.DomainColor
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toTextColor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val versionTextProvider: VersionTextProvider by KoinJavaComponent.inject(VersionTextProvider::class.java)

    private val sensorsDataViewModel: SensorsDataViewModel by viewModel()

    private var threadCounter = 0
    private fun contextFactory() = Dispatchers.Default//newSingleThreadContext("MyOwnThread_${threadCounter++}")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContent {
            val state = sensorsDataViewModel.analysedAccelerometerSampleString.collectAsState(null)
            YoScreen(state.value?.analysedSampleString.toString())
        }
//        setContentView(activityMainBinding.root)
//
//        setMainLabelText()
//        startJobs()
//        setupClickListeners()
    }

//    // TODO It should be in VM and passed as state (state values)
//    private fun startJobs() {
//        startAccelerometerCollectingJob()
//        startGyroscopeCollectingJob()
//        startMagneticFieldCollectingJob()
//        startGPSCollectingJob()
//
//        startAccelerometerCollectingStateJob()
//        startGyroscopeCollectingStateJob()
//        startMagneticFieldCollectingStateJob()
//        startGPSCollectingStateJob()
//    }

    private fun setupClickListeners() {
        with(activityMainBinding) {
            startAccButton.setOnClickListener {
                sensorsDataViewModel.onStartAccelerometerClick()
            }
            stopAccButton.setOnClickListener {
                sensorsDataViewModel.onStopAccelerometerClick()
            }
        }
    }

//    private fun startAccelerometerCollectingStateJob() {
//        lifecycleScope.launch {
//            sensorsDataViewModel.accelerometerCollectionState.collect {
//                val labelTextAndColor = getTextForSystemModuleState(it)
//                activityMainBinding.accCollectionValue.text = labelTextAndColor.first
//                activityMainBinding.accCollectionValue.setBackgroundColor(labelTextAndColor.second.toTextColor())
//            }
//        }
//    }
//
//    private fun startGyroscopeCollectingStateJob() {
//        lifecycleScope.launch {
//            sensorsDataViewModel.gyroscopeCollectionState.collect {
//                val labelTextAndColor = getTextForSystemModuleState(it)
//                activityMainBinding.gyrCollectionValue.text = labelTextAndColor.first
//                activityMainBinding.gyrCollectionValue.setBackgroundColor(labelTextAndColor.second.toTextColor())
//            }
//        }
//    }
//
//    private fun startMagneticFieldCollectingStateJob() {
//        lifecycleScope.launch {
//            sensorsDataViewModel.magneticFieldCollectionState.collect {
//                val labelTextAndColor = getTextForSystemModuleState(it)
//                activityMainBinding.mfieldCollectionValue.text = labelTextAndColor.first
//                activityMainBinding.mfieldCollectionValue.setBackgroundColor(labelTextAndColor.second.toTextColor())
//            }
//        }
//    }
//
//    private fun startGPSCollectingStateJob() {
//        lifecycleScope.launch {
//            sensorsDataViewModel.gpsCollectionState.collect {
//                val labelTextAndColor = getTextForSystemModuleState(it)
//                activityMainBinding.gpsCollectionValue.text = labelTextAndColor.first
//                activityMainBinding.gpsCollectionValue.setBackgroundColor(labelTextAndColor.second.toTextColor())
//            }
//        }
//    }

    private fun getTextForSystemModuleState(systemModuleState: SystemModuleState): Pair<String, DomainColor> {
        return when (systemModuleState) {
            is SystemModuleState.Off -> {
                if (systemModuleState == SystemModuleState.Off.OnButTimeExceeded) {
                    "TimeExceeded" to DomainColor.Orange
                } else {
                    "Off" to DomainColor.Red
                }
            }
            SystemModuleState.Starting -> "Starting" to DomainColor.Yellow
            SystemModuleState.Unknown -> "Unknown" to DomainColor.Yellow
            SystemModuleState.Working -> "Working" to DomainColor.Green
        }
    }

    private val activeAccelerometerCharts = 1//12
    private val activeGyroscopeCharts = 1//12
    private val activeMagneticFieldCharts = 1//12

//    private fun startAccelerometerCollectingJob() {
//        lifecycleScope.launch {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accSampleValue.text = it.analysedSampleString
//            }
//        }
//        accelerometerLambdas.subList(0, activeAccelerometerCharts).forEach {
//            CoroutineScope(contextFactory()).launch {
//                it.invoke()
//            }
//        }
//    }
//
//    private fun startGyroscopeCollectingJob() {
//        lifecycleScope.launch {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample) {
//                    activityMainBinding.gyrSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
//                }
//            }
//        }
//        gyroscopeLambdas.subList(0, activeGyroscopeCharts).forEach {
//            CoroutineScope(contextFactory()).launch {
//                it.invoke()
//            }
//        }
//    }
//
//    private fun startMagneticFieldCollectingJob() {
//        lifecycleScope.launch {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample) {
//                    activityMainBinding.mfieldSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
//                }
//            }
//        }
//        magneticFieldLambdas.subList(0, activeMagneticFieldCharts).forEach {
//            CoroutineScope(contextFactory()).launch {
//                it.invoke()
//            }
//        }
//    }
//
//    private fun startGPSCollectingJob() {
//        lifecycleScope.launch {
//            sensorsDataViewModel.gpsSamples.collect {
//                if (it is SensorData.GPSSample) {
//                    activityMainBinding.gpsSampleValue.text = "Lat: ${it.latitude}, Lng: ${it.longitude}"
//                }
//                if (it is SensorData.Error) {
//                    activityMainBinding.gpsSampleValue.text = "${it.e?.localizedMessage}"
//                }
//            }
//        }
//    }

    private fun setMainLabelText() {
        activityMainBinding.mainLabel.text = versionTextProvider.getAboutVersionText()
    }
//
//    private val accelerometerLambdas = listOf(
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart2.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart3.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart4.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart5.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart6.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart7.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart8.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart9.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart10.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart11.updateAccelerometerChart(it.analysedSample)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.analysedAccelerometerSampleString.collect {
//                activityMainBinding.accelerometerDataChart12.updateAccelerometerChart(it.analysedSample)
//            }
//        }
//    )
//
//    private val gyroscopeLambdas = listOf(
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart2.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart3.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart4.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart5.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart6.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart7.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart8.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart9.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart10.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart11.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.gyroscopeSamples.collect {
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart12.updateChart(it.x, it.y, it.z)
//            }
//        }
//    )
//
//    private val magneticFieldLambdas = listOf(
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart2.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart3.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart4.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart5.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart6.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart7.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart8.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart9.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart10.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart11.updateChart(it.x, it.y, it.z)
//            }
//        },
//        suspend {
//            sensorsDataViewModel.magneticFieldSamples.collect {
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart12.updateChart(it.x, it.y, it.z)
//            }
//        }
//    )
}
