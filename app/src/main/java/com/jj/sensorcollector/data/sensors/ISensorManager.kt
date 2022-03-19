package com.jj.sensorcollector.data.sensors

interface ISensorManager {

    fun startAccelerometer()
    fun startGPS()

    fun stopAccelerometer()
    fun stopGPS()
}