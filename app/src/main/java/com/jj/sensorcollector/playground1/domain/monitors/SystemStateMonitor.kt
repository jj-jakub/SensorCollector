package com.jj.sensorcollector.playground1.domain.monitors

import kotlinx.coroutines.flow.StateFlow

interface SystemStateMonitor {

    val accelerometerCollectionState: StateFlow<SystemModuleState>

    fun startMonitoring()
}