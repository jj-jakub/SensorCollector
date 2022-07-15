package com.jj.design.charts

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun MultipleValuesChart(
    linesInfo: List<List<ChartPoint>> = emptyList(),
    isDynamicChart: Boolean = false,
    modifier: Modifier = Modifier
) {
    if (linesInfo.isEmpty()) return

    val spacing = 10f
    val maxUpperValue = if (isDynamicChart) getMaximumValue(linesInfo = linesInfo)
    else remember {
        getMaximumValue(linesInfo = linesInfo)
    }
    val minLowerValue = if (isDynamicChart) getMinimumValue(linesInfo = linesInfo)
    else remember {
        getMinimumValue(linesInfo = linesInfo)
    }

    val density = LocalDensity.current
    val textPaint = remember {
        Paint().apply {
            color = android.graphics.Color.WHITE
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }

    Canvas(
        modifier = modifier.padding(vertical = 12.dp)
    ) {
        val spacePerHour = (size.width - spacing) / linesInfo.maxOf { it.size }
        drawHours(
            spacePerHour = spacePerHour,
            chartPoints = linesInfo.first(),
            spacing = spacing,
            textPaint = textPaint,
            drawScope = this
        )
        drawPriceSteps(
            spacing = spacing,
            upperValue = maxUpperValue,
            lowerValue = minLowerValue,
            textPaint = textPaint,
            drawScope = this
        )

        linesInfo.forEachIndexed { i, chartPoints ->
            val chartPaths = getChartPaths(
                spacePerHour = spacePerHour,
                spacing = spacing,
                lowerValue = minLowerValue,
                upperValue = maxUpperValue,
                chartPoints = chartPoints,
                drawScope = this
            )
            drawStrokePath(
                strokePath = chartPaths.strokePath,
                graphColor = availableColors[i % availableColors.size],
                strokeWidth = 2.dp.toPx(),
                drawScope = this
            )
        }
    }
}

private fun getMaximumValue(linesInfo: List<List<ChartPoint>>) = linesInfo.maxOf { info ->
    (info.maxOfOrNull { it.value })?.plus(1)?.roundToInt() ?: 0
}

private fun getMinimumValue(linesInfo: List<List<ChartPoint>>) = linesInfo.minOf { info ->
    (info.minOfOrNull { it.value })?.roundToInt() ?: 0
}

@Preview
@Composable
fun PreviewMultipleValuesChart() {
    MultipleValuesChart(
        modifier = Modifier
            .width(500.dp)
            .height(500.dp),
        linesInfo = listOf(
            createTestChartPoints(),
            createTestChartPoints(),
            createTestChartPoints()
        )
    )
}

private val availableColors = listOf(
    Color.Red,
    Color.Green,
    Color.Yellow,
    Color.Blue,
    Color.Magenta,
    Color.Cyan,
    Color.DarkGray
)

private fun createTestChartPoints() = listOf(
    ChartPoint(
        value = 1f
    ),
    ChartPoint(
        value = 2f
    ),
    ChartPoint(
        value = 3f
    ),
    ChartPoint(
        value = 4f
    ),
    ChartPoint(
        value = 5f
    ),
    ChartPoint(
        value = 6f
    ),
    ChartPoint(
        value = 7f
    ),
    ChartPoint(
        value = 8f
    ),
    ChartPoint(
        value = 9f
    ),
    ChartPoint(
        value = 10f
    )
)