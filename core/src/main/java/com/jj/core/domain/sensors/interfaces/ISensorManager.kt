package com.jj.core.domain.sensors.interfaces

import com.jj.core.domain.samples.SensorData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface ISensorManager {
    fun collectRawSensorSamples(): Flow<SensorData>
    fun collectIsActiveState(): StateFlow<Boolean>

//    fun observeSensorValues(): SharedFlow<T>
//    fun getSensorInfo(): String
//    fun onAdditionalDataChanged(accuracy: Int)
}