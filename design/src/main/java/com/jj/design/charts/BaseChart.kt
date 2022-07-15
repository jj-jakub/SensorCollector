package com.jj.design.charts

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
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
fun BaseChart(
    chartPoints: List<ChartPoint> = emptyList(),
    graphColor: Color = Color.Magenta,
    isDynamicChart: Boolean = false,
    modifier: Modifier = Modifier
) {
    val spacing = 10f
    val transparentGraphColor = remember {
        graphColor.copy(alpha = 0.5f)
    }
    val upperValue = if (isDynamicChart) getMaximumValue(chartPoints = chartPoints)
    else remember {
        getMaximumValue(chartPoints)
    }
    val lowerValue = if (isDynamicChart) getMinimumValue(chartPoints = chartPoints)
    else remember {
        getMinimumValue(chartPoints = chartPoints)
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
        modifier = modifier
    ) {
        val spacePerHour = (size.width - spacing) / chartPoints.size
        drawHours(
            spacePerHour = spacePerHour,
            chartPoints = chartPoints,
            spacing = spacing,
            textPaint = textPaint,
            drawScope = this
        )
        drawPriceSteps(
            spacing = spacing,
            upperValue = upperValue,
            lowerValue = lowerValue,
            textPaint = textPaint,
            drawScope = this
        )
        val chartPaths = getChartPaths(
            spacePerHour = spacePerHour,
            spacing = spacing,
            lowerValue = lowerValue,
            upperValue = upperValue,
            chartPoints = chartPoints,
            drawScope = this
        )
        drawStrokePath(
            strokePath = chartPaths.strokePath,
            graphColor = graphColor,
            strokeWidth = 3.dp.toPx(),
            drawScope = this
        )
        drawFillPath(
            fillPath = chartPaths.fillPath,
            transparentGraphColor = transparentGraphColor,
            spacing = spacing,
            drawScope = this
        )
    }
}

private fun getMaximumValue(chartPoints: List<ChartPoint>) =
    (chartPoints.maxOfOrNull { it.value })?.plus(1)?.roundToInt() ?: 0

private fun getMinimumValue(chartPoints: List<ChartPoint>) =
    (chartPoints.minOfOrNull { it.value })?.roundToInt() ?: 0

@Preview
@Composable
fun PreviewBaseChart() {
    BaseChart(
        modifier = Modifier
            .width(500.dp)
            .height(500.dp),
        chartPoints = listOf(
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
    )
}