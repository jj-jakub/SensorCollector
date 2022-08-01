package com.jj.core.framework.presentation.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jj.design.canvas.custom.Insignia
import kotlinx.coroutines.launch

@Composable
fun UITestingScreen() {
    val maxDegrees = 360F
    val maxAnimationMillis = 10000F
    val minAnimationMillis = 17 // 17 is minimum value, it stops animating at 16
    val sliderSteps = 1000

    var sliderValue by remember { mutableStateOf(maxAnimationMillis) }
    val animationScope = rememberCoroutineScope()
    val animatableDegrees = remember { Animatable(initialValue = 0f) }

    Log.d("ABAB", "Slider $sliderValue")
    // Initial start
    LaunchedEffect(key1 = Unit) {
        animationScope.launch {
            startAnimation(
                animationTimeFloat = sliderValue,
                minAnimationMillis = minAnimationMillis,
                animatableDegrees = animatableDegrees,
                maxDegrees = maxDegrees
            )
        }
    }

    // Restart if needed
    if (animatableDegrees.value >= maxDegrees) {
        LaunchedEffect(key1 = animationScope) {
            animationScope.launch {
                restartAnimation(animatableDegrees, maxDegrees, sliderValue, minAnimationMillis)
            }
        }
    }

    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        Insignia(
            rotationDegrees = animatableDegrees.value
        )
        Slider(
            modifier = Modifier.padding(50.dp),
            value = sliderValue,
            onValueChange = {
                sliderValue = it
                animationScope.launch {
                    startAnimation(
                        animationTimeFloat = it,
                        minAnimationMillis = minAnimationMillis,
                        animatableDegrees = animatableDegrees,
                        maxDegrees = maxDegrees
                    )
                }
            },
            valueRange = 0f..maxAnimationMillis,
            steps = sliderSteps
        )
    }
}

private suspend fun restartAnimation(
    animatableDegrees: Animatable<Float, AnimationVector1D>,
    maxDegrees: Float,
    sliderValue: Float,
    minAnimationMillis: Int
) {
    animatableDegrees.snapTo(0f)
    animatableDegrees.animateTo(
        maxDegrees,
        animationSpec = tween(
            durationMillis = sliderValue.toInt().coerceAtLeast(minAnimationMillis),
            easing = LinearEasing
        )
    )
}

private suspend fun startAnimation(
    animationTimeFloat: Float,
    minAnimationMillis: Int,
    animatableDegrees: Animatable<Float, AnimationVector1D>,
    maxDegrees: Float
) {
    val animationTime =
        (animationTimeFloat.toInt().coerceAtLeast(minAnimationMillis) * (1 - (animatableDegrees.value / maxDegrees))).toInt()
    animatableDegrees.animateTo(
        maxDegrees,
        animationSpec = tween(
            durationMillis = animationTime,
            easing = LinearEasing
        )
    )
}


@Preview
@Composable
fun PreviewUITestingScreen() {
    UITestingScreen()
}