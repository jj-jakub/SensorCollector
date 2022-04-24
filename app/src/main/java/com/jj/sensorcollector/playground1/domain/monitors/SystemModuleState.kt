package com.jj.sensorcollector.playground1.domain.monitors

sealed class SystemModuleState {
    object Working: SystemModuleState()
    object Starting: SystemModuleState()
    object Off: SystemModuleState() // Add some data, e.g. manually stopped or time bound exceeded
    object Unknown: SystemModuleState()
}
