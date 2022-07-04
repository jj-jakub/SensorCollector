package com.jj.core.domain.managers

interface VibrationManager {

    fun vibrate(durationMillis: Long): Boolean
}