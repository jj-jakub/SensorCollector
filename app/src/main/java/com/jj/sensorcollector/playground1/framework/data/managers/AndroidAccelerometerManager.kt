package com.jj.sensorcollector.playground1.framework.data.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager
import com.jj.sensorcollector.playground1.framework.domain.managers.AndroidSmartSensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

class AndroidAccelerometerManager(context: Context) : AccelerometerManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_ACCELEROMETER,
    scope = CoroutineScope(Dispatchers.IO) // TODO Inject
) {

    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData =
        SensorData.AccSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))


    override val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?) {
            val sensorData = convertSensorEvent(p0)
//                Log.d("ABABX", "emit")
                sensorSamples.tryEmit(sensorData)
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            /* no-op */
        }
    }
}