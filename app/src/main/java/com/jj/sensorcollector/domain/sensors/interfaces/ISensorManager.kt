package com.jj.sensorcollector.domain.sensors.interfaces

interface ISensorManager <T> {

    fun start()
    fun stop()
//    fun observeSensorValues(): SharedFlow<T>
//    fun getSensorInfo(): String
//    fun onAdditionalDataChanged(accuracy: Int)
}