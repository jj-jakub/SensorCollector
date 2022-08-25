package com.jj.hardware.data.monitoring

import com.jj.domain.monitoring.SystemStateMonitor
import com.jj.domain.monitoring.AccelerometerStateMonitor
import com.jj.domain.monitoring.GPSStateMonitor
import com.jj.domain.monitoring.GyroscopeStateMonitor
import com.jj.domain.monitoring.MagneticFieldStateMonitor

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