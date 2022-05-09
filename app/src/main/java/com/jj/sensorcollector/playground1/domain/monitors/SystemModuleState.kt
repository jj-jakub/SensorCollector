package com.jj.sensorcollector.playground1.domain.monitors

sealed class SystemModuleState {
    object Working : SystemModuleState()
    object Starting : SystemModuleState()
    open class Off : SystemModuleState() { // Add some data, e.g. manually stopped or time bound exceeded
        object OnButTimeExceeded: Off()
    }
    object Unknown : SystemModuleState()
}
