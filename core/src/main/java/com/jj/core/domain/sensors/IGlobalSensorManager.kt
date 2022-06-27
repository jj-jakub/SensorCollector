package com.jj.core.domain.sensors

import com.jj.core.data.sensors.SensorControlEvent
import com.jj.core.domain.result.DataResult

interface IGlobalSensorManager {

    suspend fun startAccelerometer(): DataResult<SensorControlEvent>
    suspend fun startGPS(): DataResult<SensorControlEvent>

    suspend fun stopAccelerometer(): DataResult<SensorControlEvent>
    suspend fun stopGPS(): DataResult<SensorControlEvent>
}