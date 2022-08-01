package com.jj.core.framework.presentation.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Slider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jj.core.framework.presentation.viewmodels.UITestingScreenViewModel
import com.jj.design.canvas.custom.Insignia
import com.jj.design.canvas.custom.Propeller
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import kotlin.math.pow

@Composable
fun UITestingScreen(
    viewModel: UITestingScreenViewModel = getViewModel()
) {
    val maxDegrees = 360F
    val minAnimationMillis = 17 // 17 is minimum value, it stops animating at 16
    val sliderSteps = 60

    /**
     * 1 per second = 1s
     * 2 per second = 0.5s
     *
     */
    val sliderValue by viewModel.sliderValueState.collectAsState()
    val animationScope = rememberCoroutineScope()
    val animatableDegrees = remember { Animatable(initialValue = 0f) }

    Log.d("ABAB", "Slider $sliderValue")
    // Initial start
    LaunchedEffect(key1 = Unit) {
        animationScope.launch {
            startAnimation(
                animationTimeFloat = viewModel.getAnimationTime(),
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
                restartAnimation(animatableDegrees, maxDegrees, viewModel.getAnimationTime(), minAnimationMillis)
            }
        }
    }

    LaunchedEffect(key1 = sliderValue) {
        animationScope.launch {
            startAnimation(
                animationTimeFloat = viewModel.getAnimationTime(),
                minAnimationMillis = minAnimationMillis,
                animatableDegrees = animatableDegrees,
                maxDegrees = maxDegrees
            )
        }
    }

    Column(
        modifier = Modifier.wrapContentSize()
    ) {
        Insignia()
        Row {
            repeat(4) {
                Propeller(
                    rotationDegrees = (animatableDegrees.value + (it * 20)) * ((-1.0).pow(it.toDouble())).toFloat()
                )
            }
        }
        Slider(
            modifier = Modifier.padding(50.dp),
            value = sliderValue,
            onValueChange = viewModel::onSliderValueChanged,
            valueRange = 0f..viewModel.maxSliderSteps,
            steps = sliderSteps
        )
        Text(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            text = sliderValue.toString()
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