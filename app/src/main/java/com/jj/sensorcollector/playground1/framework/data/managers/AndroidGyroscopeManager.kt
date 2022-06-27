package com.jj.sensorcollector.playground1.framework.data.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import com.jj.sensorcollector.playground1.domain.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.managers.GyroscopeManager
import com.jj.sensorcollector.playground1.framework.domain.managers.AndroidSmartSensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

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