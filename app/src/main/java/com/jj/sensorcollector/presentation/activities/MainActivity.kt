package com.jj.sensorcollector.presentation.activities

import android.os.Bundle
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
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
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

        setClickListeners()
        setMainLabelText()
//        startAccelerometerCollectingJob()
//        startGyroscopeCollectingJob()
//        startMagneticFieldCollectingJob()
        startGPSCollectingJob()
    }

    private fun setClickListeners() {
        with(activityMainBinding) {
            startChart.setOnClickListener { lifecycleScope.launch { startNewChart() } }
            stopChart.setOnClickListener { lifecycleScope.launch { stopLatestChart() } }
        }
    }

    private val maxActiveAccelerometerCharts = 12
    private val maxActiveGyroscopeCharts = 12
    private val maxActiveMagneticFieldCharts = 12

    private val activeChartsJobs = mutableListOf<Job>()
    private val lock = Mutex()

    private suspend fun startNewChart() {
        lock.withLock {
            if (activeChartsJobs.size < allChartLambdas.size - 1) {
                val index = activeChartsJobs.size
                activeChartsJobs.add(CoroutineScope(contextFactory()).launch {
                    try {
                        allChartLambdas[index].invoke()
                    } catch (e: Exception) {
                        Log.e("ABAB", "e: ", e)
                    }
                })
            }
        }
    }

    private suspend fun stopLatestChart() {
        lock.withLock {
            if (activeChartsJobs.isNotEmpty()) {
                activeChartsJobs.last().cancel()
                activeChartsJobs.removeLast()
            }
        }
    }

    private fun startAccelerometerCollectingJob() {
        lifecycleScope.launch {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accSampleValue.text = it.analysedSampleString
            }
        }
        accelerometerLambdas.subList(0, maxActiveAccelerometerCharts).forEach {
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
        gyroscopeLambdas.subList(0, maxActiveGyroscopeCharts).forEach {
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
        magneticFieldLambdas.subList(0, maxActiveMagneticFieldCharts).forEach {
            CoroutineScope(contextFactory()).launch {
                it.invoke()
            }
        }
    }

    private fun startGPSCollectingJob() {
        lifecycleScope.launch {
            viewModel.gpsSamples.collect {
                if (it is SensorData.GPSSample) {
                    activityMainBinding.gpsSampleValue.text = "Lat: ${it.latitude}, Lng: ${it.longitude}"
                }
                if (it is SensorData.Error) {
                    activityMainBinding.gpsSampleValue.text = "${it.e?.localizedMessage}"
                }
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

    private val allChartLambdas = accelerometerLambdas + gyroscopeLambdas + magneticFieldLambdas
}
