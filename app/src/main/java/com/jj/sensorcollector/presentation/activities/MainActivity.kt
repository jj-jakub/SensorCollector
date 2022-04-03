package com.jj.sensorcollector.presentation.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.jj.sensorcollector.data.text.VersionTextProvider
import com.jj.sensorcollector.databinding.ActivityMainBinding
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val versionTextProvider: VersionTextProvider by KoinJavaComponent.inject(VersionTextProvider::class.java)

    private val viewModel: SensorsDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setMainLabelText()

        startAccelerometerCollectingJob()
        startGyroscopeCollectingJob()
        startMagneticFieldCollectingJob()
    }

    private fun startAccelerometerCollectingJob(): Job {
        return lifecycleScope.launch {
            viewModel.accelerometerSamples.collect {
                if (it is SensorData.AccSample) {
                    activityMainBinding.accSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
                }
            }
        }
    }

    private fun startGyroscopeCollectingJob(): Job {
        return lifecycleScope.launch {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample) {
                    activityMainBinding.gyrSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
                }
            }
        }
    }

    private fun startMagneticFieldCollectingJob(): Job {
        return lifecycleScope.launch {
            viewModel.magneticFieldSamples.collect {
                if (it is SensorData.MagneticFieldSample) {
                    activityMainBinding.mfieldSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
                }
            }
        }
    }

    private fun setMainLabelText() {
        activityMainBinding.mainLabel.text = versionTextProvider.getAboutVersionText()
    }
}