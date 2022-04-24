package com.jj.sensorcollector.playground1.domain.monitors

import kotlinx.coroutines.flow.StateFlow

interface AccelerometerStateMonitor {

    val accelerometerCollectionState: StateFlow<SystemModuleState>

    fun startMonitoring()
}