package com.jj.domain.hardware.general.model

sealed class SensorInitializationResult {
    object Success : SensorInitializationResult()
    data class InitializationError(val message: String) : SensorInitializationResult()
}
