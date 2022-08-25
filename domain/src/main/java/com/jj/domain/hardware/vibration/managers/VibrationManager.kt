package com.jj.domain.hardware.vibration.managers

interface VibrationManager {

    fun vibrate(durationMillis: Long): Boolean
}