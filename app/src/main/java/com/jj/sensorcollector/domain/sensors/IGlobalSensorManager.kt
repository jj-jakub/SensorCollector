package com.jj.sensorcollector.domain.sensors

interface IGlobalSensorManager {

    fun startAccelerometer()
    fun startGPS()

    fun stopAccelerometer()
    fun stopGPS()
}