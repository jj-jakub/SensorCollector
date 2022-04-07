package com.jj.sensorcollector.playground1.framework.presentation.charts

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jj.sensorcollector.databinding.BaseLinearChartBinding
import com.jj.sensorcollector.playground1.domain.samples.analysis.AnalysedSample
import com.jj.sensorcollector.playground1.domain.ui.colors.DomainColor
import com.jj.sensorcollector.playground1.framework.ui.text.AndroidColorMapper.toTextColor

class BaseLinearChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var lineDataSetX: LineDataSet? = null
    private var lineDataSetY: LineDataSet? = null
    private var lineDataSetZ: LineDataSet? = null

    private val baseLinearChartBinding: BaseLinearChartBinding = BaseLinearChartBinding.inflate(LayoutInflater.from(context), this, true)

    fun updateAccelerometerChart(analysedSample: AnalysedSample.AnalysedAccSample) {
        analysedSample.analysedX.value?.let { value ->
            lineDataSetX?.let { dataSet ->
                dataSet.addEntry(Entry(dataSet.entryCount.toFloat(), value))
            }
        }
        analysedSample.analysedY.value?.let { value ->
            lineDataSetY?.let { dataSet ->
                dataSet.addEntry(Entry(dataSet.entryCount.toFloat(), value))
            }
        }
        analysedSample.analysedZ.value?.let { value ->
            lineDataSetZ?.let { dataSet ->
                dataSet.addEntry(Entry(dataSet.entryCount.toFloat(), value))
            }
        }
        with(baseLinearChartBinding.chart1) {
            setVisibleXRangeMaximum(10F)
            lineDataSetX?.entryCount?.toFloat()?.let { xPos -> moveViewToX(xPos) }
            data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)
            invalidate()
        }
    }

    fun setupChart() {
        with(baseLinearChartBinding.chart1) {
            lineDataSetX = getDataSet("X", DomainColor.Red) //TODO Constant
            lineDataSetY = getDataSet("Y", DomainColor.Green) //TODO Constant
            lineDataSetZ = getDataSet("Z", DomainColor.Yellow) //TODO Constant

            data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            axisRight.setDrawLabels(false)
            xAxis.setDrawLabels(false)
            xAxis.setDrawGridLines(false)
            legend.isEnabled = false
            description.isEnabled = false
            setVisibleXRangeMaximum(10F) //TODO Constant
            invalidate()
        }
    }

    private fun getDataSet(label: String, color: DomainColor): LineDataSet {
        val valueSet1 = ArrayList<Entry>()
        valueSet1.add(Entry(0f, 0f)) //TODO Constant

        val lineDataSet = LineDataSet(valueSet1, label)
        lineDataSet.setDrawValues(false)
        lineDataSet.setDrawCircles(false)
        lineDataSet.color = color.toTextColor()
        return lineDataSet
    }
}