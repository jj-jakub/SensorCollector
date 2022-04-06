package com.jj.sensorcollector.playground1.framework.domain.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.jj.sensorcollector.playground1.domain.samples.SensorData
import com.jj.sensorcollector.playground1.domain.managers.SmartSensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

abstract class AndroidSmartSensorManager(
    private val context: Context,
    private val sensorType: Int,
    scope: CoroutineScope
) : SmartSensorManager(sensorType) {

    init {
        Log.d("ABABX", "${hashCode()} init, context: $context")
        scope.launch {
            start()
        }
    }

    private var sensorManager: SensorManager? = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    private var sensor: Sensor? = sensorManager?.getDefaultSensor(sensorType)

    override fun onActive() {
        super.onActive()
        if (sensorManager == null) initializeSensorManager()
        sensorManager?.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_GAME)
        Log.d("ABABX", "listener for sensor $sensorType registered")
    }

    override fun onInactive() {
        super.onInactive()
        if (sensorManager == null) initializeSensorManager()
        sensorManager?.unregisterListener(sensorListener, sensor)
        Log.d("ABABX", "listener for sensor $sensorType unregistered")
    }

    private fun initializeSensorManager() {
        Log.d("ABABX", "${hashCode()} SType: $sensorType, ctx: $context")
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        sensor = sensorManager?.getDefaultSensor(sensorType)
        if (sensorManager == null || sensor == null) {
            val sensorData = SensorData.Error("Failed to initialize sensorManager", null)
            sensorSamples.tryEmit(sensorData)
        }
    }

    protected abstract fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData

    open val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?) {
            val sensorData = convertSensorEvent(p0)
            sensorSamples.tryEmit(sensorData)
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            /* no-op */
        }
    }
}