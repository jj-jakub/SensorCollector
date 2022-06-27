package com.jj.sensors.framework.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import com.jj.core.domain.coroutines.CoroutineScopeProvider
import com.jj.core.domain.samples.SensorData
import com.jj.sensors.framework.domain.managers.AndroidSmartSensorManager
import com.jj.core.domain.managers.MagneticFieldManager

class AndroidMagneticFieldManager(
    context: Context,
    coroutineScopeProvider: CoroutineScopeProvider
) : MagneticFieldManager, AndroidSmartSensorManager<SensorData.MagneticFieldSample>(
    context = context,
    sensorType = Sensor.TYPE_MAGNETIC_FIELD,
    scope = coroutineScopeProvider.getIOScope()
) {

    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData =
        SensorData.MagneticFieldSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))
}