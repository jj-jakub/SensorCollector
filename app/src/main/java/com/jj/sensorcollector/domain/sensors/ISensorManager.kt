package com.jj.sensorcollector.domain.sensors

interface ISensorManager {

    fun startAccelerometer()
    fun startGPS()

    fun stopAccelerometer()
    fun stopGPS()
}