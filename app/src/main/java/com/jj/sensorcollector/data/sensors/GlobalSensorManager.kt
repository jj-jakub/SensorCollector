package com.jj.sensorcollector.data.sensors

import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager
import com.jj.sensorcollector.domain.sensors.interfaces.AccelerometerManager
import com.jj.sensorcollector.domain.sensors.interfaces.GPSManager

class GlobalSensorManager(
        private val accelerometerManager: AccelerometerManager,
        private val gpsManager: GPSManager,
) : IGlobalSensorManager {

    override fun startAccelerometer() {
        accelerometerManager.start()
    }

    override fun startGPS() {
        gpsManager.start()
    }

    override fun stopAccelerometer() {
        accelerometerManager.stop()
    }

    override fun stopGPS() {
        gpsManager.stop()
    }
}