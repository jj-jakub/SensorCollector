package com.jj.sensorcollector.playground1.framework.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager
import com.jj.sensorcollector.playground1.framework.domain.AndroidSmartSensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AndroidGyroscopeManager(context: Context) : GyroscopeManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_GYROSCOPE,
    scope = CoroutineScope(Dispatchers.IO) // TODO Inject
) {

    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData =
        SensorData.GyroscopeSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))
}