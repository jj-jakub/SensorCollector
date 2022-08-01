package com.jj.core.framework.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlin.math.roundToInt

class UITestingScreenViewModel : ViewModel() {

    val maxSliderSteps = 60F
    private val oneSecondMillis = 1000L

    private val _sliderValueState = MutableStateFlow(maxSliderSteps)
    val sliderValueState = _sliderValueState.asStateFlow()

    fun onSliderValueChanged(value: Float) {
        _sliderValueState.value = value.roundToInt().toFloat()
    }

    fun getAnimationTime(): Float = oneSecondMillis / _sliderValueState.value
}