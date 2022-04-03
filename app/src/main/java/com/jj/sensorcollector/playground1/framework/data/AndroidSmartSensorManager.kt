package com.jj.sensorcollector.playground1.framework.data

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.jj.sensorcollector.playground1.domain.SensorData
import com.jj.sensorcollector.playground1.domain.managers.SmartSensorManager
import kotlinx.coroutines.CoroutineScope

abstract class AndroidSmartSensorManager(
    private val context: Context,
    private val sensorType: Int,
    scope: CoroutineScope
) : SmartSensorManager(scope) {

    private var sensorManager: SensorManager? = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    private var sensor: Sensor? = sensorManager?.getDefaultSensor(sensorType)

    override fun onActive() {
        if (sensorManager == null) initializeSensorManager()
        sensorManager?.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onInactive() {
        if (sensorManager == null) initializeSensorManager()
        sensorManager?.unregisterListener(sensorListener, sensor)
    }

    private fun initializeSensorManager() {
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        sensor = sensorManager?.getDefaultSensor(sensorType)
        if (sensorManager == null || sensor == null) {
            val sensorData = SensorData.Error("Failed to initialize sensorManager", null)
            sensorSamples.tryEmit(sensorData)
        }
    }

    private val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?) {
            val sensorData = SensorData.AccSample(p0?.values?.first(), p0?.values?.get(1), p0?.values?.get(2))
            sensorSamples.tryEmit(sensorData)
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            /* no-op */
        }
    }
}