package com.jj.sensors.framework.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import com.jj.sensorcollector.playground1.domain.coroutines.CoroutineScopeProvider
import com.jj.sensors.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager
import com.jj.sensors.framework.domain.managers.AndroidSmartSensorManager

class AndroidGyroscopeManager(
    context: Context,
    coroutineScopeProvider: CoroutineScopeProvider
) : GyroscopeManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_GYROSCOPE,
    scope = coroutineScopeProvider.getIOScope()
) {

    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData =
        SensorData.GyroscopeSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))
}