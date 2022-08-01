package com.jj.design.canvas.custom

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawOutline
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Propeller(
    modifier: Modifier = Modifier,
    width: Float = 100F,
    height: Float = 100F,
    rotationDegrees: Float = 0F,
    partWidth: Float = 20f,
    tipHeight: Float = 20f
) {
    val widthPx = LocalDensity.current.run { width.dp.toPx() }
    val heightPx = LocalDensity.current.run { height.dp.toPx() }

    val firstColor = Color.Green
    val secondColor = Color.Yellow
    val thirdColor = Color.Red

    Box(
        modifier = Modifier
            .rotate(degrees = rotationDegrees)
            .then(modifier)
    ) {
        Canvas(
            modifier = Modifier.size(
                width = width.dp,
                height = height.dp
            )
        ) {
            val startingX = 0f
            val startingY = 0f

            val path1 = Path().apply {
                moveTo(startingX, heightPx / 2)
                lineTo(widthPx, heightPx / 2)
            }

            val path2 = Path().apply {
                moveTo(widthPx / 2, startingY)
                lineTo(widthPx / 2, heightPx)
            }
            drawPath(
                path = path1,
                color = firstColor,
                style = Stroke(width = partWidth, cap = StrokeCap.Round)
            )
            drawPath(
                path = path2,
                color = secondColor,
                style = Stroke(width = partWidth, cap = StrokeCap.Round)
            )
            val path3 = Path().apply {
                moveTo(widthPx / 2 - partWidth / 2, startingY + tipHeight)
                lineTo(widthPx / 2 + partWidth / 2, startingY + tipHeight)
                lineTo(widthPx / 2, startingY)
                lineTo(widthPx / 2 - partWidth / 2, startingY + tipHeight)
            }
            drawPath(
                path = path3,
                color = thirdColor,
                style = Stroke(width = partWidth / 15)
            )
            drawIntoCanvas {
                it.drawOutline(
                    outline = Outline.Generic(path = path3),
                    paint = Paint().apply {
                        color = thirdColor
                    }
                )
            }
            val path4 = Path().apply {
                moveTo(widthPx / 2 - partWidth / 2, heightPx - tipHeight)
                lineTo(widthPx / 2 + partWidth / 2, heightPx - tipHeight)
                lineTo(widthPx / 2, heightPx)
                lineTo(widthPx / 2 - partWidth / 2, heightPx - tipHeight)
            }
            drawPath(
                path = path4,
                color = thirdColor,
                style = Stroke(width = partWidth / 15)
            )
            drawIntoCanvas {
                it.drawOutline(
                    outline = Outline.Generic(path = path4),
                    paint = Paint().apply {
                        color = thirdColor
                    }
                )
            }
            val path5 = Path().apply {
                moveTo(widthPx - tipHeight, heightPx / 2 - partWidth / 2)
                lineTo(widthPx - tipHeight, heightPx / 2 + partWidth / 2)
                lineTo(widthPx, heightPx / 2)
                lineTo(widthPx - tipHeight, heightPx / 2 - partWidth / 2)
            }
            drawPath(
                path = path5,
                color = thirdColor,
                style = Stroke(width = partWidth / 15)
            )
            drawIntoCanvas {
                it.drawOutline(
                    outline = Outline.Generic(path = path5),
                    paint = Paint().apply {
                        color = thirdColor
                    }
                )
            }
            val path6 = Path().apply {
                moveTo(startingX + tipHeight, heightPx / 2 + partWidth / 2)
                lineTo(startingX + tipHeight, heightPx / 2 - partWidth / 2)
                lineTo(startingX, heightPx / 2)
                lineTo(startingX + tipHeight, heightPx / 2 + partWidth / 2)
            }
            drawPath(
                path = path6,
                color = thirdColor,
                style = Stroke(width = partWidth / 15)
            )
            drawIntoCanvas {
                it.drawOutline(
                    outline = Outline.Generic(path = path6),
                    paint = Paint().apply {
                        color = thirdColor
                    }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewPropeller() {
    Propeller()
}