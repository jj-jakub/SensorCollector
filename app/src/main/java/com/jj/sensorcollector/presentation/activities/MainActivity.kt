package com.jj.sensorcollector.presentation.activities

import android.graphics.Color
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jj.sensorcollector.data.text.VersionTextProvider
import com.jj.sensorcollector.databinding.ActivityMainBinding
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val versionTextProvider: VersionTextProvider by KoinJavaComponent.inject(VersionTextProvider::class.java)

    private val viewModel: SensorsDataViewModel by viewModel()

    private var threadCounter = 0
    private fun contextFactory() = Dispatchers.Default//newSingleThreadContext("MyOwnThread_${threadCounter++}")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setMainLabelText()
        startAccelerometerCollectingJob()
        startGyroscopeCollectingJob()
        startMagneticFieldCollectingJob()
    }

    private val activeAccelerometerCharts = 12
    private val activeGyroscopeCharts = 12
    private val activeMagneticFieldCharts = 12

    private fun startAccelerometerCollectingJob() {
        lifecycleScope.launch {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accSampleValue.text = it.analysedSampleString
            }
        }
        accelerometerLambdas.subList(0, activeAccelerometerCharts).forEach {
            CoroutineScope(contextFactory()).launch {
                it.invoke()
            }
        }
    }

    private fun startGyroscopeCollectingJob() {
        lifecycleScope.launch {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample) {
                    activityMainBinding.gyrSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
                }
            }
        }
        gyroscopeLambdas.subList(0, activeGyroscopeCharts).forEach {
            CoroutineScope(contextFactory()).launch {
                it.invoke()
            }
        }
    }

    private fun startMagneticFieldCollectingJob() {
        lifecycleScope.launch {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample) {
                    activityMainBinding.mfieldSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
                }
            }
        }
        magneticFieldLambdas.subList(0, activeMagneticFieldCharts).forEach {
            CoroutineScope(contextFactory()).launch {
                it.invoke()
            }
        }
    }

    private fun setMainLabelText() {
        activityMainBinding.mainLabel.text = versionTextProvider.getAboutVersionText()
    }

    private val accelerometerLambdas = listOf(
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart2.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart3.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart4.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart5.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart6.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart7.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart8.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart9.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart10.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart11.updateAccelerometerChart(it.analysedSample)
            }
        },
        suspend {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accelerometerDataChart12.updateAccelerometerChart(it.analysedSample)
            }
        }
    )

    private val gyroscopeLambdas = listOf(
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart2.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart3.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart4.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart5.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart6.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart7.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart8.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart9.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart10.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart11.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample)
                    activityMainBinding.gyroscopeDataChart12.updateChart(it.x, it.y, it.z)
            }
        }
    )

    private val magneticFieldLambdas = listOf(
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart2.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart3.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart4.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart5.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart6.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart7.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart8.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart9.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart10.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart11.updateChart(it.x, it.y, it.z)
            }
        },
        suspend {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample)
                    activityMainBinding.magneticFieldDataChart12.updateChart(it.x, it.y, it.z)
            }
        }
    )
}
