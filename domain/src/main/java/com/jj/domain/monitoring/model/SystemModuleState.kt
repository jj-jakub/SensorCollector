package com.jj.domain.monitoring.model

import com.jj.domain.ui.colors.DomainColor

sealed class SystemModuleState {
    object Working : SystemModuleState()
    object Starting : SystemModuleState()
    sealed class Off : SystemModuleState() { // Add some data, e.g. manually stopped or time bound exceeded
        object OnButTimeExceeded : Off()
        object Inactive : Off()
        data class Error(val message: String): Off()
    }

    object Unknown : SystemModuleState()
}

fun SystemModuleState.toTextAndColor() = when (this) {
    is SystemModuleState.Off -> {
        if (this == SystemModuleState.Off.OnButTimeExceeded) {
            "TimeExceeded" to DomainColor.Orange
        } else if (this is SystemModuleState.Off.Error) {
            "Off ${this.message}" to DomainColor.Red
        } else {
            "Off" to DomainColor.Red
        }
    }
    SystemModuleState.Starting -> "Starting" to DomainColor.Yellow
    SystemModuleState.Unknown -> "Unknown" to DomainColor.Yellow
    SystemModuleState.Working -> "Working" to DomainColor.Green
}
