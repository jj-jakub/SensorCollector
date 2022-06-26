package com.jj.sensorcollector.playground1.domain.monitors

import com.jj.sensorcollector.playground1.domain.ui.colors.DomainColor

sealed class SystemModuleState {
    object Working : SystemModuleState()
    object Starting : SystemModuleState()
    open class Off : SystemModuleState() { // Add some data, e.g. manually stopped or time bound exceeded
        object OnButTimeExceeded : Off()
    }

    object Unknown : SystemModuleState()
}

fun SystemModuleState.toTextAndColor() = when (this) {
    is SystemModuleState.Off -> {
        if (this == SystemModuleState.Off.OnButTimeExceeded) {
            "TimeExceeded" to DomainColor.Orange
        } else {
            "Off" to DomainColor.Red
        }
    }
    SystemModuleState.Starting -> "Starting" to DomainColor.Yellow
    SystemModuleState.Unknown -> "Unknown" to DomainColor.Yellow
    SystemModuleState.Working -> "Working" to DomainColor.Green
}
