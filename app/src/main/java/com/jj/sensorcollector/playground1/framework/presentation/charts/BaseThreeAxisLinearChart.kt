package com.jj.sensorcollector.playground1.framework.presentation.charts

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jj.sensorcollector.databinding.BaseLinearChartBinding
import com.jj.sensorcollector.playground1.domain.ui.colors.DomainColor
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toTextColor

open class BaseThreeAxisLinearChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val lineDataSetX: LineDataSet
    private val lineDataSetY: LineDataSet
    private val lineDataSetZ: LineDataSet

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
        return lineDataSet
    }

    private fun setupChart() {
        with(baseLinearChartBinding.lineChart) {
            data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)

            removeGridAndLabels(this)
            removePadding(this)

            setBackgroundColor(Color.GRAY) //TODO Style
            setVisibleXRangeMaximum(10F) //TODO Constant
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
        xAxisValue?.let { value ->
            lineDataSetX.let { dataSet ->
                cleanupDataset(dataSet)
                dataSet.addEntry(Entry(dataSet.entryCount.toFloat(), value))
            }
        }
        yAxisValue?.let { value ->
            lineDataSetY.let { dataSet ->
                cleanupDataset(dataSet)
                dataSet.addEntry(Entry(dataSet.entryCount.toFloat(), value))
            }
        }
        zAxisValue?.let { value ->
            lineDataSetZ.let { dataSet ->
                cleanupDataset(dataSet)
                dataSet.addEntry(Entry(dataSet.entryCount.toFloat(), value))
            }
        }
        with(baseLinearChartBinding.lineChart) {
            setVisibleXRangeMaximum(10F) //TODO Constant
//            setVisibleYRangeMaximum(calculateYMax(), YAxis.AxisDependency.RIGHT)
            lineDataSetX.entryCount.toFloat().let { xPos -> moveViewToX(xPos) }
            data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)
            invalidate()
        }
    }

    private fun cleanupDataset(dataSet: LineDataSet) {
        // TODO
    }

    private fun calculateYMax(): Float {
        val xMax = lineDataSetX.values.toList().map { it.y }.maxOrNull() ?: 10f // TODO DEFAULT_RANGE or something
        val yMax = lineDataSetY.values.toList().map { it.y }.maxOrNull() ?: 10f
        val zMax = lineDataSetZ.values.toList().map { it.y }.maxOrNull() ?: 10f

        return listOf(xMax, yMax, zMax).maxOrNull() ?: 10f
    }
}