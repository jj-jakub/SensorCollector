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
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jj.sensorcollector.data.text.VersionTextProvider
import com.jj.sensorcollector.databinding.ActivityMainBinding
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.ui.colors.DomainColor
import com.jj.sensorcollector.playground1.framework.presentation.SensorsDataViewModel
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toTextColor
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.java.KoinJavaComponent
import kotlin.math.abs


class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val versionTextProvider: VersionTextProvider by KoinJavaComponent.inject(VersionTextProvider::class.java)

    private val viewModel: SensorsDataViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setMainLabelText()
        setupChart()

        startAccelerometerCollectingJob()
        startGyroscopeCollectingJob()
        startMagneticFieldCollectingJob()
    }

    private fun setupChart() {
        with(activityMainBinding.accelerometerDataChart) {
            lineDataSetX = getDataSetX()
            lineDataSetY = getDataSetY()
            lineDataSetZ = getDataSetZ()
            data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)
            axisLeft.setDrawLabels(false)
            axisRight.setDrawLabels(false)
            xAxis.setDrawLabels(false)
            animateXY(2000, 2000)
            invalidate()
        }
    }

    private lateinit var lineDataSetX: LineDataSet
    private lateinit var lineDataSetY: LineDataSet
    private lateinit var lineDataSetZ: LineDataSet

    // TODO Extract me to something like BaseChart
    private fun getDataSetX(): LineDataSet {
        val valueSet1 = ArrayList<Entry>()
        val v1e1 = Entry(0f, 0f)
        valueSet1.add(v1e1)

        val lineDataSet = LineDataSet(valueSet1, "X")
        lineDataSet.setDrawCircles(false)
        lineDataSet.color = DomainColor.Red.toTextColor()
        return lineDataSet
    }
    private fun getDataSetY(): LineDataSet {
        val valueSet1 = ArrayList<Entry>()
        val v1e1 = Entry(0f, 0f)
        valueSet1.add(v1e1)

        val lineDataSet = LineDataSet(valueSet1, "Y")
        lineDataSet.setDrawCircles(false)
        lineDataSet.color = DomainColor.Green.toTextColor()
        return lineDataSet
    }
    private fun getDataSetZ(): LineDataSet {
        val valueSet1 = ArrayList<Entry>()
        val v1e1 = Entry(0f, 0f)
        valueSet1.add(v1e1)

        val lineDataSet = LineDataSet(valueSet1, "Z")
        lineDataSet.setDrawCircles(false)
        lineDataSet.color = DomainColor.Yellow.toTextColor()
        return lineDataSet
    }

    private fun startAccelerometerCollectingJob(): Job {
        return lifecycleScope.launch {
            viewModel.analysedAccelerometerSampleString.collect {
                activityMainBinding.accSampleValue.text = it.analysedSampleString
                updateAccelerometerChart(it.analysedSample)
            }
        }
    }

    private fun updateAccelerometerChart(analysedSample: AnalysedSample.AnalysedAccSample) {
        analysedSample.analysedX.value?.let { value ->
            lineDataSetX.addEntry(Entry(lineDataSetX.entryCount.toFloat(), value))
        }
        analysedSample.analysedY.value?.let { value ->
            lineDataSetY.addEntry(Entry(lineDataSetY.entryCount.toFloat(), value))
        }
        analysedSample.analysedZ.value?.let { value ->
            lineDataSetZ.addEntry(Entry(lineDataSetZ.entryCount.toFloat(), value))
        }
        with(activityMainBinding.accelerometerDataChart) {
            data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)
            notifyDataSetChanged()
            invalidate()
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