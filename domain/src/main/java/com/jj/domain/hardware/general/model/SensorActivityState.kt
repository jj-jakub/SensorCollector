package com.jj.domain.hardware.general.model

sealed class SensorActivityState {
    object Active : SensorActivityState()
    sealed class Off : SensorActivityState() {
        object Inactive : Off()
        data class Error(val message: String) : Off()
    }
}

fun SensorActivityState.isActive() = this == SensorActivityState.Active
