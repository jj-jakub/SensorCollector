package com.jj.sensorcollector.data.sensors

import com.jj.sensorcollector.domain.sensors.IGlobalSensorManager
import com.jj.sensorcollector.domain.sensors.interfaces.ISensorManager
import com.jj.sensorcollector.domain.sensors.SensorData.AccelerometerData
import com.jj.sensorcollector.domain.sensors.SensorData.GPSData

class GlobalSensorManager(
        private val accelerometerManager: ISensorManager<AccelerometerData>,
        private val gpsManager: ISensorManager<GPSData>,
): IGlobalSensorManager {

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