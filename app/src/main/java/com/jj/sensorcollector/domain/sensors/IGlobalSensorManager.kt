package com.jj.sensorcollector.domain.sensors

import com.jj.sensorcollector.data.sensors.SensorControlEvent
import com.jj.sensorcollector.domain.result.DataResult

interface IGlobalSensorManager {

    fun startAccelerometer(): DataResult<SensorControlEvent>
    fun startGPS(): DataResult<SensorControlEvent>

    fun stopAccelerometer(): DataResult<SensorControlEvent>
    fun stopGPS(): DataResult<SensorControlEvent>
}