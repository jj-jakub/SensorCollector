package com.jj.sensors.domain.monitors

import com.jj.core.domain.monitors.SystemModuleState
import kotlinx.coroutines.flow.StateFlow

interface SampleCollectionStateMonitor {

    val sampleCollectionState: StateFlow<SystemModuleState>

    fun startMonitoring()
}