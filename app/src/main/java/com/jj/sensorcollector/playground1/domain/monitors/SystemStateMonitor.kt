package com.jj.sensorcollector.playground1.domain.monitors

import kotlinx.coroutines.flow.StateFlow

interface SystemStateMonitor {

    val accelerometerCollectionState: StateFlow<SystemModuleState>
    val gyroscopeCollectionState: StateFlow<SystemModuleState>
    val magneticFieldCollectionState: StateFlow<SystemModuleState>
    val gpsCollectionState: StateFlow<SystemModuleState>

    fun startMonitoring()
}