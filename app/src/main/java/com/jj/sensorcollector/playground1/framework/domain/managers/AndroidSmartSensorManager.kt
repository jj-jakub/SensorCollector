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
) : SmartSensorManager() {

    init {
        Log.d("ABABX", "${hashCode()} init, context: $context")
        scope.launch {
            start()
        }
    }

    private var sensorManager: SensorManager? = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
    private var sensor: Sensor? = sensorManager?.getDefaultSensor(sensorType)

    override suspend fun onActive(): Boolean {
        Log.d("ABABC", "onActive, starting sensor listener, type $sensorType")
        if (sensorManager == null || sensor == null) initializeSensorManager()
        val registered = sensorManager?.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_GAME)
        if (registered != true) {
            onError("Failed to register listener")
        } else {
            Log.d("ABABX", "listener for sensor $sensorType registered")
        }
        return registered == true
    }

    override fun onInactive() {
        super.onInactive()
        Log.d("ABABC", "onInactive, stopping sensor listener, type $sensorType")
        if (sensorManager == null || sensor == null) initializeSensorManager()
        sensorManager?.unregisterListener(sensorListener, sensor)
        Log.d("ABABX", "listener for sensor $sensorType unregistered")
    }

    private fun initializeSensorManager() {
        Log.d("ABABX", "${hashCode()} SType: $sensorType, ctx: $context")
        sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?
        sensor = sensorManager?.getDefaultSensor(sensorType)
        if (sensorManager == null || sensor == null) {
            onError("Failed to initialize sensorManager")
        }
    }

    private fun onError(errorMessage: String) {
        val sensorData = SensorData.Error(SensorData.ErrorType.InitializationFailure(errorMessage), null)
        sensorSamples.tryEmit(sensorData)
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