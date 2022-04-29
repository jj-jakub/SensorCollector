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

    private var sensorManager: SensorManager? = getSensorManager()
    private var sensor: Sensor? = sensorManager?.getDefaultSensor(sensorType)

    override suspend fun onActive(): Boolean {
        Log.d("ABABC", "onActive, starting sensor listener, type $sensorType")
        if (!sensorInitialized()) initializeSensorManager()
        val registered = sensorManager?.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_GAME) == true
        if (!registered) {
            onInitializationError("Failed to register listener")
        } else {
            Log.d("ABABX", "listener for sensor $sensorType registered")
        }
        return registered
    }

    override fun onInactive() {
        super.onInactive()
        Log.d("ABABC", "onInactive, stopping sensor listener, type $sensorType")
        if (!sensorInitialized()) initializeSensorManager()
        sensorManager?.unregisterListener(sensorListener, sensor)
        Log.d("ABABX", "listener for sensor $sensorType unregistered")
    }

    private fun initializeSensorManager() {
        Log.d("ABABX", "${hashCode()} SType: $sensorType, ctx: $context")
        sensorManager = getSensorManager()
        sensor = sensorManager?.getDefaultSensor(sensorType)
        if (!sensorInitialized()) {
            onInitializationError("Failed to initialize sensorManager")
        }
    }

    private fun onInitializationError(errorMessage: String) {
        val sensorData = SensorData.Error(SensorData.ErrorType.InitializationFailure(errorMessage), null)
        sensorSamples.tryEmit(sensorData)
    }

    private fun getSensorManager() = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?

    private fun sensorInitialized() = sensorManager != null && sensor != null

    protected open val sensorListener = object : SensorEventListener {
        override fun onSensorChanged(p0: SensorEvent?) {
            val sensorData = convertSensorEvent(p0)
            sensorSamples.tryEmit(sensorData)
        }

        override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
            /* no-op */
        }
    }

    protected abstract fun convertSensorEvent(sensorEvent: SensorEvent?): SensorData
}