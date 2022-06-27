package com.jj.sensors.framework.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.util.Log
import com.jj.sensorcollector.playground1.domain.coroutines.CoroutineScopeProvider
import com.jj.sensors.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.managers.AccelerometerManager
import com.jj.sensors.framework.domain.managers.AndroidSmartSensorManager

class AndroidAccelerometerManager(
    context: Context,
    coroutineScopeProvider: CoroutineScopeProvider
) : AccelerometerManager, AndroidSmartSensorManager(
    context = context,
    sensorType = Sensor.TYPE_ACCELEROMETER,
    scope = coroutineScopeProvider.getIOScope() // TODO Inject
) {

    override fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData =
        SensorData.AccSample(sensorEvent?.values?.first(), sensorEvent?.values?.get(1), sensorEvent?.values?.get(2))


    override val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?) {
            val sensorData = convertSensorEvent(p0)
            Log.d("ABABX", "emit: ${p0?.values?.get(0)} ${p0?.values?.get(1)} ${p0?.values?.get(2)}")
            sensorSamples.tryEmit(sensorData)
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            /* no-op */
        }
    }
}