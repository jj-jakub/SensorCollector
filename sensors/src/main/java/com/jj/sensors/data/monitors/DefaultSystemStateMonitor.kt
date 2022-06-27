package com.jj.sensors.data.monitors

import com.jj.core.domain.monitors.SystemStateMonitor
import com.jj.sensors.domain.monitors.markers.AccelerometerStateMonitor
import com.jj.sensors.domain.monitors.markers.GPSStateMonitor
import com.jj.sensors.domain.monitors.markers.GyroscopeStateMonitor
import com.jj.sensors.domain.monitors.markers.MagneticFieldStateMonitor

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