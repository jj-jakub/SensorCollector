package com.jj.sensorcollector.playground1.framework.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.managers.MagneticFieldManager
import com.jj.sensorcollector.playground1.framework.domain.AndroidSmartSensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AndroidMagneticFieldManager(context: Context) : MagneticFieldManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_MAGNETIC_FIELD,
    scope = CoroutineScope(Dispatchers.IO) // TODO Inject
) {

    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData =
        SensorData.MagneticFieldSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))
}