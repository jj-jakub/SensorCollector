package com.jj.sensorcollector.playground1.framework.presentation.charts

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jj.sensorcollector.databinding.BaseLinearChartBinding
import com.jj.sensorcollector.playground1.domain.ui.colors.DomainColor
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toTextColor
import java.lang.Integer.max

private const val MAX_VISIBLE_SAMPLES = 100
private const val MAX_CACHED_SAMPLES = 200

open class BaseThreeAxisLinearChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val lineDataSetX: LineDataSet
    private val lineDataSetY: LineDataSet
    private val lineDataSetZ: LineDataSet

    private var xDataCounter = 0
    private var yDataCounter = 0
    private var zDataCounter = 0

    private val baseLinearChartBinding: BaseLinearChartBinding = BaseLinearChartBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        lineDataSetX = getDataSet("X", DomainColor.Red) //TODO Constant/Style
        lineDataSetY = getDataSet("Y", DomainColor.Green) //TODO Constant/Style
        lineDataSetZ = getDataSet("Z", DomainColor.Yellow) //TODO Constant/Style
        setupChart()
    }

    private fun getDataSet(label: String, color: DomainColor): LineDataSet {
        val valueSet1 = ArrayList<Entry>()
        valueSet1.add(Entry(0f, 0f)) //TODO Constant

        val lineDataSet = LineDataSet(valueSet1, label)
        lineDataSet.lineWidth = 2f //TODO Constant
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawCircles(false)
        lineDataSet.color = color.toTextColor()
        lineDataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
        return lineDataSet
    }

    private fun setupChart() {
        with(baseLinearChartBinding.lineChart) {
            data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)

            removeGridAndLabels(this)
            removePadding(this)

            setBackgroundColor(Color.GRAY) //TODO Style
            setVisibleXRangeMaximum(MAX_VISIBLE_SAMPLES.toFloat()) //TODO Constant
            invalidate()
        }
    }

    private fun removeGridAndLabels(lineChart: LineChart) {
        with(lineChart) {
            xAxis.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
        }
    }

    private fun removePadding(lineChart: LineChart) {
        lineChart.setViewPortOffsets(0f, 0f, 0f, 0f)
    }

    fun updateChart(xAxisValue: Float?, yAxisValue: Float?, zAxisValue: Float?) {
        try {
            xAxisValue?.let { value ->
                lineDataSetX.let { dataSet ->
                    cleanupDataset(dataSet)
                    dataSet.addEntry(Entry(xDataCounter++.toFloat(), value))
                }
            }

            yAxisValue?.let { value ->
                lineDataSetY.let { dataSet ->
                    cleanupDataset(dataSet)
                    dataSet.addEntry(Entry(yDataCounter++.toFloat(), value))
                }
            }

            zAxisValue?.let { value ->
                lineDataSetZ.let { dataSet ->
                    cleanupDataset(dataSet)
                    dataSet.addEntry(Entry(zDataCounter++.toFloat(), value))
                }
            }

            with(baseLinearChartBinding.lineChart) {
                setVisibleXRangeMaximum(MAX_VISIBLE_SAMPLES.toFloat()) //TODO Constant
                moveViewToX(max(0, xDataCounter - MAX_VISIBLE_SAMPLES - 1).toFloat())
                data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)
                invalidate()
            }

            checkSamplesCounters()
        } catch (e: Exception) {
            Log.e("ABAB", "e:", e)
        }
    }

    private fun checkSamplesCounters() {
        if (xDataCounter == Int.MAX_VALUE || yDataCounter == Int.MAX_VALUE || zDataCounter == Int.MAX_VALUE) {
            xDataCounter = 0
            yDataCounter = 0
            zDataCounter = 0

            clearDataSet(lineDataSetX)
            clearDataSet(lineDataSetY)
            clearDataSet(lineDataSetZ)
        }
    }

    private fun cleanupDataset(dataSet: LineDataSet) {
        if (dataSet.entryCount == MAX_CACHED_SAMPLES) {
            dataSet.removeFirst()
        }
    }

    private fun clearDataSet(lineDataSet: LineDataSet) {
        with(lineDataSet) {
            clear()
            addEntry(Entry(0f, 0f))
            calcMinMaxY(0f, 1f)
        }
    }
}