package com.jj.hardware.data.hardware.gyroscope.manager

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.gyroscope.manager.GyroscopeManager
import com.jj.domain.hardware.model.SensorData
import com.jj.hardware.data.hardware.general.AndroidSmartSensorManager

class AndroidGyroscopeManager(
    context: Context,
    coroutineScopeProvider: CoroutineScopeProvider
) : GyroscopeManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_GYROSCOPE,
    scope = coroutineScopeProvider.getIOScope()
) {
    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData.GyroscopeSample =
        SensorData.GyroscopeSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))
}