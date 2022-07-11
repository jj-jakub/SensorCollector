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

data class ChartPoint(val value: Float)
data class ChartPaths(val strokePath: Path, val fillPath: Path)

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


private fun drawHours(
    spacePerHour: Float,
    chartPoints: List<ChartPoint>,
    spacing: Float,
    textPaint: Paint,
    drawScope: DrawScope
) {
    (0 until chartPoints.size - 1 step 2).forEach { i ->
        drawScope.drawContext.canvas.nativeCanvas.apply {
            drawText(
                i.toString(),
                spacing + i * spacePerHour,
                drawScope.size.height - 5,
                textPaint
            )
        }
    }
}

private fun drawPriceSteps(
    spacing: Float,
    upperValue: Int,
    lowerValue: Int,
    textPaint: Paint,
    drawScope: DrawScope
) {
    val priceStep = (upperValue - lowerValue) / 5f
    (1..5).forEach { i ->
        drawScope.drawContext.canvas.nativeCanvas.apply {
            drawText(
                round(lowerValue + priceStep * i).toString(),
                30f,
                drawScope.size.height - spacing - i * drawScope.size.height / 5f,
                textPaint
            )
        }
    }
}

private fun getChartPaths(
    spacePerHour: Float,
    spacing: Float,
    upperValue: Int,
    lowerValue: Int,
    chartPoints: List<ChartPoint>,
    drawScope: DrawScope
): ChartPaths {
    var lastX = 0f
    val strokePath = Path().apply {
        val height = drawScope.size.height
        for (i in chartPoints.indices) {
            val info = chartPoints[i]
            val nextInfo = chartPoints.getOrNull(i + 1) ?: chartPoints.last()
            val leftRatio = (info.value - lowerValue) / (upperValue - lowerValue)
            val rightRatio = (nextInfo.value - lowerValue) / (upperValue - lowerValue)

            val x1 = spacing + i * spacePerHour
            val y1 = height - spacing - (leftRatio * height)
            val x2 = spacing + (i + 1) * spacePerHour
            val y2 = height - spacing - (rightRatio * height)
            if (i == 0) {
                moveTo(x = x1, y = y1)
            }
            lastX = (x1 + x2) / 2f
            quadraticBezierTo(
                x1 = x1,
                y1 = y1,
                x2 = lastX,
                y2 = (y1 + y2) / 2f
            )
        }
    }
    val fillPath = android.graphics.Path(strokePath.asAndroidPath())
        .asComposePath()
        .apply {
            lineTo(x = lastX, y = drawScope.size.height - spacing)
            lineTo(x = spacing, y = drawScope.size.height - spacing)
            close()
        }
    return ChartPaths(strokePath = strokePath, fillPath = fillPath)
}

private fun drawStrokePath(strokePath: Path, graphColor: Color, strokeWidth: Float, drawScope: DrawScope) {
    drawScope.drawPath(
        path = strokePath,
        color = graphColor,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}

private fun drawFillPath(fillPath: Path, transparentGraphColor: Color, spacing: Float, drawScope: DrawScope) {
    drawScope.drawPath(
        path = fillPath,
        brush = Brush.verticalGradient(
            colors = listOf(
                transparentGraphColor,
                Color.Transparent
            ),
            endY = drawScope.size.height - spacing
        )
    )
}

@Preview
@Composable
fun PreviewStockChart() {
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