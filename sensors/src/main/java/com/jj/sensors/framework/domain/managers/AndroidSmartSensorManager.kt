package com.jj.sensors.framework.domain.managers

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import com.jj.core.domain.samples.SensorData
import com.jj.core.domain.managers.SmartSensorManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Input -> collectRawSensorSamples
 *      Output -> Sample
 *      Output -> Error
 * Input -> collectIsActiveState
 *      Output -> Boolean state
 */
abstract class AndroidSmartSensorManager<T>(
    private val context: Context,
    private val sensorType: Int,
    scope: CoroutineScope
) : SmartSensorManager<T>() {

    init {
        scope.launch {
            start()
        }
    }

    private var sensorManager: SensorManager? = getSensorManager()
    private var sensor: Sensor? = sensorManager?.getDefaultSensor(sensorType)

    override suspend fun onActive(): Boolean {
        if (sensorNotInitialized()) initializeSensorManager()
        val registered = sensorManager?.registerListener(sensorListener, sensor, SensorManager.SENSOR_DELAY_GAME)
        if (registered != true) {
            onInitializationError("Failed to register listener")
        }
        return registered == true
    }

    override fun onInactive() {
        super.onInactive()
        sensorManager?.unregisterListener(sensorListener, sensor)
    }

    private fun initializeSensorManager() {
        sensorManager = getSensorManager()
        sensor = sensorManager?.getDefaultSensor(sensorType)
    }

    private fun onInitializationError(errorMessage: String) {
        val sensorData = SensorData.Error(SensorData.ErrorType.InitializationFailure(errorMessage), null)
        sensorSamples.tryEmit(sensorData)
    }

    private fun getSensorManager(): SensorManager? = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager?

    private fun sensorInitialized() = sensorManager != null && sensor != null

    private fun sensorNotInitialized() = !sensorInitialized()

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