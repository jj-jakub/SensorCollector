package com.jj.domain.monitoring

import com.jj.domain.monitoring.model.SystemModuleState
import kotlinx.coroutines.flow.StateFlow

interface SampleCollectionStateMonitor {

    val sampleCollectionState: StateFlow<SystemModuleState>

    fun startMonitoring()
}