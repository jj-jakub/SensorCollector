package com.jj.sensorcollector.playground1.domain.ui

import android.text.Spannable

sealed class UISample { // TODO Extract me to framework please
    data class AccelerometerUISample(val x: Spannable, val y: Spannable, val z: Spannable) : UISample()
    // TODO Error sample
}