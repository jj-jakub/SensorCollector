package com.jj.sensorcollector.playground1.domain.monitors

sealed class SystemModuleState {
    object Working: SystemModuleState()
    object Starting: SystemModuleState()
    object Off: SystemModuleState()
    object Unknown: SystemModuleState()
}
