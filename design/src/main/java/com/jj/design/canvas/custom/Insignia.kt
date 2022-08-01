package com.jj.design.canvas.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Insignia(
    modifier: Modifier = Modifier,
    rotationDegrees: Float = 0f,
    pathWidth: Float = 20f,
    subBoxWidth: Float = 100f
) {
    val subBoxWidthPx = LocalDensity.current.run { subBoxWidth.dp.toPx() }
    val pathWidthPx = LocalDensity.current.run { pathWidth.dp.toPx() }
    val firstColor = Color.White
    val secondColor = Color.Red

    Box(
        modifier = Modifier
            .background(Color.Gray)
            .rotate(degrees = rotationDegrees)
            .padding(pathWidth.dp / 2)
            .then(modifier)
    ) {
        Canvas(
            modifier = Modifier.size(
                width = subBoxWidth.dp * 2,
                height = subBoxWidth.dp * 2
            )
        ) {
            val startingX = 0f
            val startingY = 0f

            val path = Path().apply {
                moveTo(startingX - pathWidthPx / 2, startingY)
                lineTo(startingX + subBoxWidthPx, startingY)
                moveTo(startingX, startingY)
                lineTo(startingX, startingY + subBoxWidthPx)
            }
            val path2 = Path().apply {
                moveTo(startingX + subBoxWidthPx, startingY)
                lineTo(startingX + subBoxWidthPx * 2, startingY)
                lineTo(startingX + subBoxWidthPx * 2, startingY + subBoxWidthPx)
            }
            val path3 = Path().apply {
                moveTo(startingX + subBoxWidthPx * 2, startingY + subBoxWidthPx)
                lineTo(startingX + subBoxWidthPx * 2, startingY + subBoxWidthPx * 2)
                lineTo(startingX + subBoxWidthPx, startingY + subBoxWidthPx * 2)
            }
            val path4 = Path().apply {
                moveTo(startingX + subBoxWidthPx, startingY + subBoxWidthPx * 2)
                lineTo(startingX, startingY + subBoxWidthPx * 2)
                lineTo(startingX, startingY + subBoxWidthPx)
            }
            drawRect(
                color = secondColor,
                topLeft = Offset(startingX, startingY),
                size = Size(subBoxWidthPx, subBoxWidthPx)
            )
            drawRect(
                color = firstColor,
                topLeft = Offset(startingX + subBoxWidthPx, startingY),
                size = Size(subBoxWidthPx, subBoxWidthPx)
            )
            drawRect(
                color = secondColor,
                topLeft = Offset(startingX + subBoxWidthPx, startingY + subBoxWidthPx),
                size = Size(subBoxWidthPx, subBoxWidthPx)
            )
            drawRect(
                color = firstColor,
                topLeft = Offset(startingX, startingY + subBoxWidthPx),
                size = Size(subBoxWidthPx, subBoxWidthPx)
            )
            drawPath(
                path = path,
                color = firstColor,
                style = Stroke(width = pathWidthPx),
            )
            drawPath(
                path = path2,
                color = secondColor,
                style = Stroke(width = pathWidthPx),
            )
            drawPath(
                path = path3,
                color = firstColor,
                style = Stroke(width = pathWidthPx)
            )
            drawPath(
                path = path4,
                color = secondColor,
                style = Stroke(width = pathWidthPx),
            )
        }
    }
}

@Preview
@Composable
fun PreviewInsignia() {
    Insignia()
}