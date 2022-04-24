package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.playground1.domain.monitors.AccelerometerStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.monitors.SystemStateMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultSystemStateMonitor(
    private val accelerometerStateMonitor: AccelerometerStateMonitor
) : SystemStateMonitor {

    override val accelerometerCollectionState = accelerometerStateMonitor.accelerometerCollectionState

    private val _gyroscopeCollectionState = MutableStateFlow(SystemModuleState.Unknown)
    val gyroscopeCollectionState = _gyroscopeCollectionState.asStateFlow()

    private val _magneticFieldCollectionState = MutableStateFlow(SystemModuleState.Unknown)
    val magneticFieldCollectionState = _magneticFieldCollectionState.asStateFlow()

    private val _gpsCollectionState = MutableStateFlow(SystemModuleState.Unknown)
    val gpsCollectionState = _gpsCollectionState.asStateFlow()

    override fun startMonitoring() {
        accelerometerStateMonitor.startMonitoring()
    }
}