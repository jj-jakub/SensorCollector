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
    private fun contextFactory() = newSingleThreadContext("MyOwnThread_${threadCounter++}")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setMainLabelText()
        startAccelerometerCollectingJob()
        startGyroscopeCollectingJob()
        startMagneticFieldCollectingJob()
    }

    //    private fun startAccelerometerCollectingJob(): Job = lifecycleScope.launch {
//        viewModel.analysedAccelerometerSampleString.collect {
//            activityMainBinding.accSampleValue.text = it.analysedSampleString
//            activityMainBinding.accelerometerDataChart.updateAccelerometerChart(it.analysedSample)
//            activityMainBinding.accelerometerDataChart2.updateAccelerometerChart(it.analysedSample)
//            activityMainBinding.accelerometerDataChart3.updateAccelerometerChart(it.analysedSample)
//            activityMainBinding.accelerometerDataChart4.updateAccelerometerChart(it.analysedSample)
//
//            activityMainBinding.accelerometerDataChart5.updateAccelerometerChart(it.analysedSample)
//            activityMainBinding.accelerometerDataChart6.updateAccelerometerChart(it.analysedSample)
//            activityMainBinding.accelerometerDataChart7.updateAccelerometerChart(it.analysedSample)
//            activityMainBinding.accelerometerDataChart8.updateAccelerometerChart(it.analysedSample)
//        }
//    }

    private fun startAccelerometerCollectingJob() {
        Log.d("ABAB", "thread: ${Thread.currentThread()}")
//        viewModel.magneticFieldSamples.collect {
//            if (it is SensorData.MagneticFieldSample) {
//                activityMainBinding.mfieldSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
//            }
        lifecycleScope.launch {
            viewModel.analysedAccelerometerSampleString.collect {
                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
                activityMainBinding.accelerometerDataChart.updateAccelerometerChart(it.analysedSample)
            }
        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.analysedAccelerometerSampleString.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                activityMainBinding.accelerometerDataChart.updateAccelerometerChart(it.analysedSample)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.analysedAccelerometerSampleString.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                activityMainBinding.accelerometerDataChart2.updateAccelerometerChart(it.analysedSample)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.analysedAccelerometerSampleString.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                activityMainBinding.accelerometerDataChart3.updateAccelerometerChart(it.analysedSample)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.analysedAccelerometerSampleString.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                activityMainBinding.accelerometerDataChart4.updateAccelerometerChart(it.analysedSample)
//            }
//        }
//
//        CoroutineScope(contextFactory()).launch {
//            viewModel.analysedAccelerometerSampleString.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                activityMainBinding.accelerometerDataChart5.updateAccelerometerChart(it.analysedSample)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.analysedAccelerometerSampleString.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                activityMainBinding.accelerometerDataChart6.updateAccelerometerChart(it.analysedSample)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.analysedAccelerometerSampleString.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                activityMainBinding.accelerometerDataChart7.updateAccelerometerChart(it.analysedSample)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.analysedAccelerometerSampleString.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                activityMainBinding.accelerometerDataChart8.updateAccelerometerChart(it.analysedSample)
//            }
//        }
    }

//    private fun startGyroscopeCollectingJob(): Job = lifecycleScope.launch {
//        viewModel.gyroscopeSamples.collect {
//            if (it is SensorData.GyroscopeSample) {
//                activityMainBinding.gyrSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.gyroscopeDataChart.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.gyroscopeDataChart2.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.gyroscopeDataChart3.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.gyroscopeDataChart4.updateChart(it.x, it.y, it.z)
//                }
//
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.gyroscopeDataChart5.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.gyroscopeDataChart6.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.gyroscopeDataChart7.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.gyroscopeDataChart8.updateChart(it.x, it.y, it.z)
//                }
//            }
//        }
//    }

    private fun startGyroscopeCollectingJob() {
        Log.d("ABAB", "thread: ${Thread.currentThread()}")
//        viewModel.magneticFieldSamples.collect {
//            if (it is SensorData.MagneticFieldSample) {
//                activityMainBinding.mfieldSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
////            }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.gyroscopeSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.gyroscopeSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart2.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.gyroscopeSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart3.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.gyroscopeSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart4.updateChart(it.x, it.y, it.z)
//            }
//        }
//
//        CoroutineScope(contextFactory()).launch {
//            viewModel.gyroscopeSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart5.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.gyroscopeSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart6.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.gyroscopeSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart7.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.gyroscopeSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.GyroscopeSample)
//                    activityMainBinding.gyroscopeDataChart8.updateChart(it.x, it.y, it.z)
//            }
//        }
    }
//    private fun startMagneticFieldCollectingJob(): Job = lifecycleScope.launch {
//        viewModel.magneticFieldSamples.collect {
//            if (it is SensorData.MagneticFieldSample) {
//                activityMainBinding.mfieldSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.magneticFieldDataChart.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.magneticFieldDataChart2.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.magneticFieldDataChart3.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.magneticFieldDataChart4.updateChart(it.x, it.y, it.z)
//                }
//
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.magneticFieldDataChart5.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.magneticFieldDataChart6.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.magneticFieldDataChart7.updateChart(it.x, it.y, it.z)
//                }
//                CoroutineScope(contextFactory()).launch {
//    Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                    activityMainBinding.magneticFieldDataChart8.updateChart(it.x, it.y, it.z)
//                }
//            }
//        }
//    }

    private fun startMagneticFieldCollectingJob() {
//        viewModel.magneticFieldSamples.collect {
//            if (it is SensorData.MagneticFieldSample) {
//                activityMainBinding.mfieldSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
//            }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.magneticFieldSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.magneticFieldSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart2.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.magneticFieldSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart3.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.magneticFieldSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart4.updateChart(it.x, it.y, it.z)
//            }
//        }
//
//        CoroutineScope(contextFactory()).launch {
//            viewModel.magneticFieldSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart5.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.magneticFieldSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart6.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.magneticFieldSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart7.updateChart(it.x, it.y, it.z)
//            }
//        }
//        CoroutineScope(contextFactory()).launch {
//            viewModel.magneticFieldSamples.collect {
//                Log.d("ABAB", "working in thread ${Thread.currentThread().name}")
//                if (it is SensorData.MagneticFieldSample)
//                    activityMainBinding.magneticFieldDataChart8.updateChart(it.x, it.y, it.z)
//            }
//        }
    }

    private fun setMainLabelText() {
        activityMainBinding.mainLabel.text = versionTextProvider.getAboutVersionText()
    }
}