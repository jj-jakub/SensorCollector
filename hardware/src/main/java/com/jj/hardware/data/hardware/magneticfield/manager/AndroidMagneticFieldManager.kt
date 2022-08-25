package com.jj.hardware.data.hardware.magneticfield.manager

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import com.jj.domain.coroutines.CoroutineScopeProvider
import com.jj.domain.hardware.model.SensorData
import com.jj.hardware.data.hardware.general.AndroidSmartSensorManager
import com.jj.domain.hardware.magneticfield.manager.MagneticFieldManager

class AndroidMagneticFieldManager(
    context: Context,
    coroutineScopeProvider: CoroutineScopeProvider
) : MagneticFieldManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_MAGNETIC_FIELD,
    scope = coroutineScopeProvider.getIOScope()
) {

    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData =
        SensorData.MagneticFieldSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))
}