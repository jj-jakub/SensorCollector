package com.jj.sensorcollector.playground1.framework.data.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import com.jj.sensorcollector.playground1.domain.coroutines.CoroutineScopeProvider
import com.jj.sensorcollector.playground1.domain.managers.MagneticFieldManager
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.framework.domain.managers.AndroidSmartSensorManager

class AndroidMagneticFieldManager(
    context: Context,
    coroutineScopeProvider: CoroutineScopeProvider
) : MagneticFieldManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_MAGNETIC_FIELD,
    scope = coroutineScopeProvider.getIOScope() // TODO Inject
) {

    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData =
        SensorData.MagneticFieldSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))
}