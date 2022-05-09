package com.jj.sensorcollector.playground1.domain.monitors

import kotlinx.coroutines.flow.StateFlow

interface SampleCollectionStateMonitor {

    val sampleCollectionState: StateFlow<SystemModuleState>

    fun startMonitoring()
}