package com.jj.design.charts

import android.graphics.Paint
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.asAndroidPath
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import kotlin.math.round

fun drawHours(
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

fun drawPriceSteps(
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

fun getChartPaths(
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

fun drawStrokePath(strokePath: Path, graphColor: Color, strokeWidth: Float, drawScope: DrawScope) {
    drawScope.drawPath(
        path = strokePath,
        color = graphColor,
        style = Stroke(
            width = strokeWidth,
            cap = StrokeCap.Round
        )
    )
}

fun drawFillPath(fillPath: Path, transparentGraphColor: Color, spacing: Float, drawScope: DrawScope) {
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