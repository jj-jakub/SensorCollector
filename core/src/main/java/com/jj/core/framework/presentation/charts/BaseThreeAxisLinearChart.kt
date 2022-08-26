package com.jj.core.framework.presentation.charts

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.jj.core.databinding.BaseLinearChartBinding
import com.jj.core.framework.text.AndroidColorMapper.toTextColor
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.ui.colors.DomainColor
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlinx.coroutines.withContext
import org.koin.java.KoinJavaComponent.inject
import java.lang.Integer.max

/**
 * MAX_VISIBLE_SAMPLES - really slows UI
 * MAX_CACHED_SAMPLES - important for max/min calculations, i.e. when chart will recalibrate,
 * but also seems to slow down UI (probably due to recalculations given more samples)
 *
 * Any UI updates on chart, i.e. data assignment, invalidation - must be run on Main thread, or
 * exceptions will be thrown like NegativeIndexException or IndexOutOfBoundsException
 *
 * Mutex lock seems to not be necessary, as we update section on single Main thread, but it is there for
 * better safety, as updating chart and cleaning up dataset is a critical section. Otherwise one thread
 * will update chart given MAX_CACHED_SAMPLES, and second one can clear up dataset before UI update happens.
 *
 * updateChart method can be run on any thread. Dispatchers.Default seems to be faster than singleContextThreads,
 * even though Dispatchers.Default seems to have 12 threads (and we have 36 charts).
 */
private const val MAX_VISIBLE_SAMPLES = 10
private const val MAX_CACHED_SAMPLES = 200

open class BaseThreeAxisLinearChart @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private val chartUpdateLock = Mutex()

    private val lineDataSetX: LineDataSet
    private val lineDataSetY: LineDataSet
    private val lineDataSetZ: LineDataSet

    private var xDataCounter = 0
    private var yDataCounter = 0
    private var zDataCounter = 0

    private val coroutineScopeProvider: CoroutineScopeProvider by inject(CoroutineScopeProvider::class.java)

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
            setVisibleXRangeMaximum(MAX_VISIBLE_SAMPLES.toFloat())
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

    suspend fun updateChart(xAxisValue: Float?, yAxisValue: Float?, zAxisValue: Float?) {

        xAxisValue?.let { value -> lineDataSetX.addEntry(Entry(xDataCounter++.toFloat(), value)) }
        yAxisValue?.let { value -> lineDataSetY.addEntry(Entry(yDataCounter++.toFloat(), value)) }
        zAxisValue?.let { value -> lineDataSetZ.addEntry(Entry(zDataCounter++.toFloat(), value)) }

        chartUpdateLock.withLock { invalidateChart() }
    }

    // Must be run on Main thread
    private suspend fun invalidateChart() {
        withContext(coroutineScopeProvider.main) {
            with(baseLinearChartBinding.lineChart) {
                setVisibleXRangeMaximum(MAX_VISIBLE_SAMPLES.toFloat())
                data = LineData(lineDataSetX, lineDataSetY, lineDataSetZ)
                moveViewToX(max(0, xDataCounter - MAX_VISIBLE_SAMPLES - 1).toFloat())
                invalidate()
                cleanupDataset(lineDataSetX)
                cleanupDataset(lineDataSetY)
                cleanupDataset(lineDataSetZ)
                checkSamplesCounters()
            }
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