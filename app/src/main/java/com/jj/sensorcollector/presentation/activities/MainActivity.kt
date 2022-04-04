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
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.ui.UISample
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.abs

interface ColorStrBuilder<FrameworkColorStringType> {
    fun buildStr(): FrameworkColorStringType
}

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
                activityMainBinding.accSampleValue.text = it
            }
        }
    }

    private fun spanNumber(num: Float?): SpannableString? {
        return if (num == null) num
        else {
            val absNum = abs(num.toDouble())
            val strNum = num.toString()
            if (absNum < 5) {
                return SpannableString(strNum).apply {
                    setSpan(
                        ForegroundColorSpan(Color.GREEN),
                        0,
                        strNum.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            } else if (absNum >= 5 && absNum < 9) {
                return SpannableString(strNum).apply {
                    setSpan(
                        ForegroundColorSpan(Color.YELLOW),
                        0,
                        strNum.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            } else {
                return SpannableString(strNum).apply {
                    setSpan(
                        ForegroundColorSpan(Color.RED),
                        0,
                        strNum.length,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
    }

    private fun startGyroscopeCollectingJob(): Job {
        return lifecycleScope.launch {
            viewModel.gyroscopeSamples.collect {
                if (it is SensorData.GyroscopeSample) {
                    activityMainBinding.gyrSampleValue.text =
                        SpannableStringBuilder().append("X: ").append(spanNumber(it.x)).append(", Y: ").append(spanNumber(it.y))
                            .append(", Z: ").append(spanNumber(it.z))
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