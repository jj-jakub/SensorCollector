package com.jj.sensorcollector.domain.sensors

import com.jj.sensorcollector.domain.sensors.SensorData.AccelerometerData
import kotlinx.coroutines.flow.SharedFlow

interface ISensorManager <T> {

    fun start()
    fun stop()
//    fun observeSensorValues(): SharedFlow<T>
//    fun getSensorInfo(): String
//    fun onAdditionalDataChanged(accuracy: Int)
}