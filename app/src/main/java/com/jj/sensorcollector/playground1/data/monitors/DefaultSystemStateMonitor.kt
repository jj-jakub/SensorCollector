package com.jj.sensorcollector.playground1.data.monitors

import com.jj.sensorcollector.playground1.domain.monitors.markers.AccelerometerStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.SystemModuleState
import com.jj.sensorcollector.playground1.domain.monitors.SystemStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.markers.GPSStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.markers.GyroscopeStateMonitor
import com.jj.sensorcollector.playground1.domain.monitors.markers.MagneticFieldStateMonitor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class DefaultSystemStateMonitor(
    private val accelerometerStateMonitor: AccelerometerStateMonitor,
    private val gyroscopeStateMonitor: GyroscopeStateMonitor,
    private val magneticFieldStateMonitor: MagneticFieldStateMonitor,
    private val gpsStateMonitor: GPSStateMonitor,
) : SystemStateMonitor {

    override val accelerometerCollectionState = accelerometerStateMonitor.sampleCollectionState

    override val gyroscopeCollectionState = gyroscopeStateMonitor.sampleCollectionState

    override val magneticFieldCollectionState = magneticFieldStateMonitor.sampleCollectionState

    override val gpsCollectionState = gpsStateMonitor.sampleCollectionState

    override fun startMonitoring() {
        accelerometerStateMonitor.startMonitoring()
        gyroscopeStateMonitor.startMonitoring()
        magneticFieldStateMonitor.startMonitoring()
        gpsStateMonitor.startMonitoring()
    }
}