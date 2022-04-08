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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val versionTextProvider: VersionTextProvider by KoinJavaComponent.inject(VersionTextProvider::class.java)

    private val viewModel: SensorsDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setMainLabelText()
        try {
            startAccelerometerCollectingJob()
            startGyroscopeCollectingJob()
            startMagneticFieldCollectingJob()
        } catch (e: Exception) {
            Log.e("ABAB", "e:", e)
        }
    }

    private fun startAccelerometerCollectingJob(): Job = CoroutineScope(Dispatchers.Default).launch {//lifecycleScope.launch {
        viewModel.analysedAccelerometerSampleString.collect {
//            activityMainBinding.accSampleValue.text = it.analysedSampleString
//            activityMainBinding.accelerometerDataChart.updateAccelerometerChart(it.analysedSample)
        }
    }

    private fun startGyroscopeCollectingJob(): Job = CoroutineScope(Dispatchers.Default).launch {//lifecycleScope.launch {
        viewModel.gyroscopeSamples.collect {
            if (it is SensorData.GyroscopeSample) {
//                activityMainBinding.gyrSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
                activityMainBinding.gyroscopeDataChart.updateChart(it.x, it.y, it.z)
            }
        }
    }

    private fun startMagneticFieldCollectingJob(): Job = CoroutineScope(Dispatchers.Default).launch {//lifecycleScope.launch {
        viewModel.magneticFieldSamples.collect {
            if (it is SensorData.MagneticFieldSample) {
//                activityMainBinding.mfieldSampleValue.text = "X: ${it.x}, Y: ${it.y}, Z: ${it.z}"
                activityMainBinding.magneticFieldDataChart.updateChart(it.x, it.y, it.z)
            }
        }
    }

    private fun setMainLabelText() {
        activityMainBinding.mainLabel.text = versionTextProvider.getAboutVersionText()
    }
}